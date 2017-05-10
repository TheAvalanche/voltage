import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {JhiLanguageService} from 'ng-jhipster';

import {Blog} from '../entities/blog/blog.model';
import {PublicBlogService} from './public-blog.service';

@Component({
    selector: 'jhi-public-blog-detail',
    templateUrl: './public-blog-detail.component.html',
    styleUrls: [
        'public-blog-detail.scss'
    ]
})
export class PublicBlogDetailComponent implements OnInit, OnDestroy {

    blog: Blog;
    private subscription: any;


    constructor(private jhiLanguageService: JhiLanguageService,
                private blogService: PublicBlogService,
                private route: ActivatedRoute) {
        this.jhiLanguageService.setLocations(['blog', 'language']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load(id) {
        this.blogService.find(id).subscribe(blog => {
            this.blog = blog;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}
