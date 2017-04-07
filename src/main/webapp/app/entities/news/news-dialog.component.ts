import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService, DataUtils } from 'ng-jhipster';

import { News } from './news.model';
import { NewsPopupService } from './news-popup.service';
import { NewsService } from './news.service';

@Component({
    selector: 'jhi-news-dialog',
    templateUrl: './news-dialog.component.html'
})
export class NewsDialogComponent implements OnInit {

    news: News;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private alertService: AlertService,
        private newsService: NewsService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['news', 'language']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData($event, news, field, isImage) {
        if ($event.target.files && $event.target.files[0]) {
            let $file = $event.target.files[0];
            if (isImage && !/^image\//.test($file.type)) {
                return;
            }
            this.dataUtils.toBase64($file, (base64Data) => {
                news[field] = base64Data;
                news[`${field}ContentType`] = $file.type;
            });
        }
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.news.id !== undefined) {
            this.newsService.update(this.news)
                .subscribe((res: News) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.newsService.create(this.news)
                .subscribe((res: News) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess (result: News) {
        this.eventManager.broadcast({ name: 'newsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-news-popup',
    template: ''
})
export class NewsPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private newsPopupService: NewsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.newsPopupService
                    .open(NewsDialogComponent, params['id']);
            } else {
                this.modalRef = this.newsPopupService
                    .open(NewsDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
