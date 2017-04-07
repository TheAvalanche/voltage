import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { BlogCategory } from './blog-category.model';
import { BlogCategoryPopupService } from './blog-category-popup.service';
import { BlogCategoryService } from './blog-category.service';

@Component({
    selector: 'jhi-blog-category-delete-dialog',
    templateUrl: './blog-category-delete-dialog.component.html'
})
export class BlogCategoryDeleteDialogComponent {

    blogCategory: BlogCategory;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private blogCategoryService: BlogCategoryService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['blogCategory', 'language']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.blogCategoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'blogCategoryListModification',
                content: 'Deleted an blogCategory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-blog-category-delete-popup',
    template: ''
})
export class BlogCategoryDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private blogCategoryPopupService: BlogCategoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.blogCategoryPopupService
                .open(BlogCategoryDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
