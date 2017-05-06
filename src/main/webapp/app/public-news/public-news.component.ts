import {Component, OnInit, OnDestroy} from '@angular/core';
import {Response} from '@angular/http';

import { ITEMS_PER_PAGE, Principal } from '../shared';
import { ParseLinks, JhiLanguageService, AlertService } from 'ng-jhipster';
import {News} from '../entities/news/news.model';
import {PublicNewsService} from './public-news.service';
import { TranslateService, LangChangeEvent } from 'ng2-translate/ng2-translate';
import {Subscription} from 'rxjs';

@Component({
    selector: 'jhi-public-news',
    templateUrl: './public-news.component.html',
    styleUrls: [
        'public-news.scss'
    ]
})
export class PublicNewsComponent implements OnInit, OnDestroy {

    news: News[];
    error: any;
    success: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    previousPage: any;
    predicate: any;
    reverse: any;
    languageChangeSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private newsService: PublicNewsService,
        private parseLinks: ParseLinks,
        private alertService: AlertService,
        private translateService: TranslateService,
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 1;
        this.previousPage = 1;
        this.jhiLanguageService.setLocations(['news', 'language']);
    }

    ngOnInit() {
        this.loadAll();

        this.registerLanguageChange();
    }

    loadPage (page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.loadAll();
        }
    }

    registerLanguageChange() {
        this.languageChangeSubscriber = this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
            this.loadAll();
        });
    }

    loadAll() {
        this.newsService.queryByCurrentLanguage({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: ['id,desc']}).subscribe(
            (res: Response) => this.onSuccess(res.json(), res.headers),
            (res: Response) => this.onError(res.json())
        );
    }

    ngOnDestroy() {
        this.languageChangeSubscriber.unsubscribe();
    }

    private onSuccess (data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        this.news = data;
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

}
