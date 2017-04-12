import {Component, OnInit} from '@angular/core';
import {Response} from '@angular/http';

import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { ParseLinks, JhiLanguageService, AlertService } from 'ng-jhipster';
import {News} from '../../entities/news/news.model';
import { NewsService } from '../../entities/news/news.service';
import {ActivatedRoute} from '@angular/router';

@Component({
    selector: 'jhi-public-news',
    templateUrl: './public-news.component.html',
    styleUrls: [
        'public-news.scss'
    ]
})
export class PublicNewsComponent implements OnInit {

    news: News[];
    error: any;
    success: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    reverse: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private newsService: NewsService,
        private parseLinks: ParseLinks,
        private alertService: AlertService,
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.jhiLanguageService.setLocations(['news', 'language']);
    }

    ngOnInit() {
        this.loadAll();
    }

    loadAll() {
        this.newsService.query({
            page: 0,
            size: this.itemsPerPage,
            sort: ['id,desc']}).subscribe(
            (res: Response) => this.onSuccess(res.json(), res.headers),
            (res: Response) => this.onError(res.json())
        );
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
        this.news = data;
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

}
