import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , DataUtils } from 'ng-jhipster';

import { Blog } from './blog.model';
import { BlogService } from './blog.service';

@Component({
    selector: 'jhi-blog-detail',
    templateUrl: './blog-detail.component.html'
})
export class BlogDetailComponent implements OnInit, OnDestroy {

    blog: Blog;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private blogService: BlogService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['blog', 'language']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInBlogs();
    }

    load (id) {
        this.blogService.find(id).subscribe(blog => {
            this.blog = blog;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBlogs() {
        this.eventSubscriber = this.eventManager.subscribe('blogListModification', response => this.load(this.blog.id));
    }

}
