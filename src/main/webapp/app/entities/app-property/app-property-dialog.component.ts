import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { AppProperty } from './app-property.model';
import { AppPropertyPopupService } from './app-property-popup.service';
import { AppPropertyService } from './app-property.service';

@Component({
    selector: 'jhi-app-property-dialog',
    templateUrl: './app-property-dialog.component.html'
})
export class AppPropertyDialogComponent implements OnInit {

    appProperty: AppProperty;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private appPropertyService: AppPropertyService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['appProperty', 'appPropertyType', 'language']);
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
        if (this.appProperty.id !== undefined) {
            this.appPropertyService.update(this.appProperty)
                .subscribe((res: AppProperty) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.appPropertyService.create(this.appProperty)
                .subscribe((res: AppProperty) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess (result: AppProperty) {
        this.eventManager.broadcast({ name: 'appPropertyListModification', content: 'OK'});
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
    selector: 'jhi-app-property-popup',
    template: ''
})
export class AppPropertyPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private appPropertyPopupService: AppPropertyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.appPropertyPopupService
                    .open(AppPropertyDialogComponent, params['id']);
            } else {
                this.modalRef = this.appPropertyPopupService
                    .open(AppPropertyDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
