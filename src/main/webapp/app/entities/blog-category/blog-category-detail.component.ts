import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { BlogCategory } from './blog-category.model';
import { BlogCategoryService } from './blog-category.service';

@Component({
    selector: 'jhi-blog-category-detail',
    templateUrl: './blog-category-detail.component.html'
})
export class BlogCategoryDetailComponent implements OnInit, OnDestroy {

    blogCategory: BlogCategory;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private blogCategoryService: BlogCategoryService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['blogCategory', 'language']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInBlogCategories();
    }

    load (id) {
        this.blogCategoryService.find(id).subscribe(blogCategory => {
            this.blogCategory = blogCategory;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBlogCategories() {
        this.eventSubscriber = this.eventManager.subscribe('blogCategoryListModification', response => this.load(this.blogCategory.id));
    }

}
