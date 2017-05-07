import {Component, OnInit, OnDestroy} from '@angular/core';
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {JhiLanguageService, AlertService, ParseLinks} from 'ng-jhipster';

import {Account, LoginModalService, Principal} from '../shared';
import {PublicBlogCategoryService} from './public-blog-category.service';
import {Response} from '@angular/http';
import {BlogCategory} from '../entities/blog-category/blog-category.model';
import {Subscription} from 'rxjs';
import {Blog} from '../entities/blog/blog.model';
import {PublicBlogService} from './public-blog.service';
import {ActivatedRoute} from '@angular/router';
import {ITEMS_PER_PAGE} from '../shared/constants/pagination.constants';

@Component({
    selector: 'jhi-public-blog',
    templateUrl: './public-blog.component.html',
    styleUrls: [
        'public-blog.scss'
    ]

})
export class PublicBlogComponent implements OnInit, OnDestroy {
    account: Account;
    modalRef: NgbModalRef;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    previousPage: any;
    blogs: Blog[];
    blogCategories: BlogCategory[];
    subscription: Subscription;

    constructor(private jhiLanguageService: JhiLanguageService,
                private parseLinks: ParseLinks,
                private route: ActivatedRoute,
                private alertService: AlertService,
                private blogService: PublicBlogService,
                private blogCategoryService: PublicBlogCategoryService) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 1;
        this.previousPage = 1;
        this.jhiLanguageService.setLocations(['blog', 'blogCategory']);
    }

    ngOnInit() {
        this.loadAllCategories();

        this.subscription = this.route.params.subscribe(params => {
            this.loadAllBlogs(params['category']);
        });
    }

    loadPage (page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.loadAllBlogs();
        }
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    loadAllBlogs(id?: number) {
        console.log(id);
        let req = {
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: ['id,desc']};
        let onSuccess = (res: Response) => this.onBlogsSuccess(res.json(), res.headers);
        let onError = (res: Response) => this.onError(res.json());
        if (id) {
            this.blogService.queryByCategory(id, req).subscribe(onSuccess, onError);
        } else {
            this.blogService.queryByCurrentLanguage(req).subscribe(onSuccess, onError);
        }
    }


    loadAllCategories() {
        this.blogCategoryService.queryByCurrentLanguage().subscribe(
            (res: Response) => this.onBlogCategoriesSuccess(res.json()),
            (res: Response) => this.onError(res.json())
        );
    }

    private onBlogsSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        this.blogs = data;
    }

    private onBlogCategoriesSuccess(data) {
        this.blogCategories = data;
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
