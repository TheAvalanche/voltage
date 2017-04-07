import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { BlogCategory } from './blog-category.model';
import { BlogCategoryPopupService } from './blog-category-popup.service';
import { BlogCategoryService } from './blog-category.service';

@Component({
    selector: 'jhi-blog-category-dialog',
    templateUrl: './blog-category-dialog.component.html'
})
export class BlogCategoryDialogComponent implements OnInit {

    blogCategory: BlogCategory;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private blogCategoryService: BlogCategoryService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['blogCategory', 'language']);
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
        if (this.blogCategory.id !== undefined) {
            this.blogCategoryService.update(this.blogCategory)
                .subscribe((res: BlogCategory) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.blogCategoryService.create(this.blogCategory)
                .subscribe((res: BlogCategory) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess (result: BlogCategory) {
        this.eventManager.broadcast({ name: 'blogCategoryListModification', content: 'OK'});
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
    selector: 'jhi-blog-category-popup',
    template: ''
})
export class BlogCategoryPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private blogCategoryPopupService: BlogCategoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.blogCategoryPopupService
                    .open(BlogCategoryDialogComponent, params['id']);
            } else {
                this.modalRef = this.blogCategoryPopupService
                    .open(BlogCategoryDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
