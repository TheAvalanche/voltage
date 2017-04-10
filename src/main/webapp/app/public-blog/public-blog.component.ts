import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Account, LoginModalService, Principal } from '../shared';

@Component({
    selector: 'jhi-home',
    templateUrl: './public-blog.component.html',
    styleUrls: [
        'public-blog.scss'
    ]

})
export class PublicBlogComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['blog']);
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
    }
}
