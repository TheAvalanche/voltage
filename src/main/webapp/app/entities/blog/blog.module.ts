import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoltageSharedModule } from '../../shared';

import {
    BlogService,
    BlogPopupService,
    BlogComponent,
    BlogDialogComponent,
    BlogPopupComponent,
    BlogDeletePopupComponent,
    BlogDeleteDialogComponent,
    blogRoute,
    blogPopupRoute,
    BlogResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...blogRoute,
    ...blogPopupRoute,
];

@NgModule({
    imports: [
        VoltageSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BlogComponent,
        BlogDialogComponent,
        BlogDeleteDialogComponent,
        BlogPopupComponent,
        BlogDeletePopupComponent,
    ],
    entryComponents: [
        BlogComponent,
        BlogDialogComponent,
        BlogPopupComponent,
        BlogDeleteDialogComponent,
        BlogDeletePopupComponent,
    ],
    providers: [
        BlogService,
        BlogPopupService,
        BlogResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoltageBlogModule {}
