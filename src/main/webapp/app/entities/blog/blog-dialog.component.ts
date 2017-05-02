import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService, DataUtils } from 'ng-jhipster';

import { Blog } from './blog.model';
import { BlogPopupService } from './blog-popup.service';
import { BlogService } from './blog.service';
import { BlogCategory, BlogCategoryService } from '../blog-category';

@Component({
    selector: 'jhi-blog-dialog',
    templateUrl: './blog-dialog.component.html'
})
export class BlogDialogComponent implements OnInit {

    blog: Blog;
    authorities: any[];
    isSaving: boolean;

    blogcategories: BlogCategory[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private alertService: AlertService,
        private blogService: BlogService,
        private blogCategoryService: BlogCategoryService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['blog', 'language']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.blogCategoryService.query().subscribe(
            (res: Response) => { this.blogcategories = res.json(); }, (res: Response) => this.onError(res.json()));
    }

    updateBodyFunction(update: any) {
        this.blog.body = update;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData($event, blog, field, isImage) {
        if ($event.target.files && $event.target.files[0]) {
            let $file = $event.target.files[0];
            if (isImage && !/^image\//.test($file.type)) {
                return;
            }
            this.dataUtils.toBase64($file, (base64Data) => {
                blog[field] = base64Data;
                blog[`${field}ContentType`] = $file.type;
            });
        }
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.blog.id !== undefined) {
            this.blogService.update(this.blog)
                .subscribe((res: Blog) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.blogService.create(this.blog)
                .subscribe((res: Blog) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess (result: Blog) {
        this.eventManager.broadcast({ name: 'blogListModification', content: 'OK'});
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

    trackBlogCategoryById(index: number, item: BlogCategory) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-blog-popup',
    template: ''
})
export class BlogPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private blogPopupService: BlogPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.blogPopupService
                    .open(BlogDialogComponent, params['id']);
            } else {
                this.modalRef = this.blogPopupService
                    .open(BlogDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
