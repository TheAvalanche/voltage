import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { News } from './news.model';
import { NewsPopupService } from './news-popup.service';
import { NewsService } from './news.service';

@Component({
    selector: 'jhi-news-delete-dialog',
    templateUrl: './news-delete-dialog.component.html'
})
export class NewsDeleteDialogComponent {

    news: News;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private newsService: NewsService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['news', 'language']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.newsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'newsListModification',
                content: 'Deleted an news'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-news-delete-popup',
    template: ''
})
export class NewsDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private newsPopupService: NewsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.newsPopupService
                .open(NewsDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
