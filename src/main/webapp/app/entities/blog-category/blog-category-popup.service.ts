import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { BlogCategory } from './blog-category.model';
import { BlogCategoryService } from './blog-category.service';
@Injectable()
export class BlogCategoryPopupService {
    private isOpen = false;
    constructor (
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private blogCategoryService: BlogCategoryService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.blogCategoryService.find(id).subscribe(blogCategory => {
                blogCategory.created = this.datePipe
                    .transform(blogCategory.created, 'yyyy-MM-ddThh:mm');
                blogCategory.updated = this.datePipe
                    .transform(blogCategory.updated, 'yyyy-MM-ddThh:mm');
                this.blogCategoryModalRef(component, blogCategory);
            });
        } else {
            return this.blogCategoryModalRef(component, new BlogCategory());
        }
    }

    blogCategoryModalRef(component: Component, blogCategory: BlogCategory): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.blogCategory = blogCategory;
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
