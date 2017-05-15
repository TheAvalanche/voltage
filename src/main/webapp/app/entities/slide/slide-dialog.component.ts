import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Slide } from './slide.model';
import { SlidePopupService } from './slide-popup.service';
import { SlideService } from './slide.service';

@Component({
    selector: 'jhi-slide-dialog',
    templateUrl: './slide-dialog.component.html'
})
export class SlideDialogComponent implements OnInit {

    slide: Slide;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private slideService: SlideService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['slide', 'language']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.slide.id !== undefined) {
            this.slideService.update(this.slide)
                .subscribe((res: Slide) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.slideService.create(this.slide)
                .subscribe((res: Slide) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess (result: Slide) {
        this.eventManager.broadcast({ name: 'slideListModification', content: 'OK'});
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
    selector: 'jhi-slide-popup',
    template: ''
})
export class SlidePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private slidePopupService: SlidePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.slidePopupService
                    .open(SlideDialogComponent, params['id']);
            } else {
                this.modalRef = this.slidePopupService
                    .open(SlideDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
