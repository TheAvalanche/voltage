import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';

import {News} from '../../entities/news/news.model';
import {PublicNewsService} from './public-news.service';

@Component({
    selector: 'jhi-public-news-detail',
    templateUrl: './public-news-detail.component.html',
    styleUrls: [
        'public-news-detail.scss'
    ]
})
export class PublicNewsDetailComponent implements OnInit, OnDestroy {

    news: News;
    private subscription: any;


    constructor(
        private jhiLanguageService: JhiLanguageService,
        private newsService: PublicNewsService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['news', 'language']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.newsService.find(id).subscribe(news => {
            this.news = news;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}
