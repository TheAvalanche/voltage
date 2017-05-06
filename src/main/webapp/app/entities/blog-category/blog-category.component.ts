import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, JhiLanguageService, AlertService } from 'ng-jhipster';

import { BlogCategory } from './blog-category.model';
import { BlogCategoryService } from './blog-category.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { TranslateService, LangChangeEvent } from 'ng2-translate/ng2-translate';

@Component({
    selector: 'jhi-blog-category',
    templateUrl: './blog-category.component.html'
})
export class BlogCategoryComponent implements OnInit, OnDestroy {

currentAccount: any;
    blogCategories: BlogCategory[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    languageChangeSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private blogCategoryService: BlogCategoryService,
        private parseLinks: ParseLinks,
        private alertService: AlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: EventManager,
        private translateService: TranslateService
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
        this.jhiLanguageService.setLocations(['blogCategory', 'language']);
    }

    loadAll() {
        this.blogCategoryService.queryByCurrentLanguage({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()}).subscribe(
            (res: Response) => this.onSuccess(res.json(), res.headers),
            (res: Response) => this.onError(res.json())
        );
    }
    loadPage (page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }
    transition() {
        this.router.navigate(['/blog-category'], {queryParams:
            {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/blog-category', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInBlogCategories();
        this.registerLanguageChange();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
        this.languageChangeSubscriber.unsubscribe();
    }

    trackId (index: number, item: BlogCategory) {
        return item.id;
    }

    registerLanguageChange() {
        this.languageChangeSubscriber = this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
            this.loadAll();
        });
    }

    registerChangeInBlogCategories() {
        this.eventSubscriber = this.eventManager.subscribe('blogCategoryListModification', (response) => this.loadAll());
    }

    sort () {
        let result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess (data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.blogCategories = data;
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
