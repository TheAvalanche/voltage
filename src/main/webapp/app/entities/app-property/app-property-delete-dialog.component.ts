import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { AppProperty } from './app-property.model';
import { AppPropertyPopupService } from './app-property-popup.service';
import { AppPropertyService } from './app-property.service';

@Component({
    selector: 'jhi-app-property-delete-dialog',
    templateUrl: './app-property-delete-dialog.component.html'
})
export class AppPropertyDeleteDialogComponent {

    appProperty: AppProperty;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private appPropertyService: AppPropertyService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['appProperty', 'appPropertyType', 'language']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.appPropertyService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'appPropertyListModification',
                content: 'Deleted an appProperty'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-app-property-delete-popup',
    template: ''
})
export class AppPropertyDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private appPropertyPopupService: AppPropertyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.appPropertyPopupService
                .open(AppPropertyDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
