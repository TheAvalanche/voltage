import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { AppProperty } from './app-property.model';
import { AppPropertyService } from './app-property.service';
@Injectable()
export class AppPropertyPopupService {
    private isOpen = false;
    constructor (
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private appPropertyService: AppPropertyService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.appPropertyService.find(id).subscribe(appProperty => {
                this.appPropertyModalRef(component, appProperty);
            });
        } else {
            return this.appPropertyModalRef(component, new AppProperty());
        }
    }

    appPropertyModalRef(component: Component, appProperty: AppProperty): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.appProperty = appProperty;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
