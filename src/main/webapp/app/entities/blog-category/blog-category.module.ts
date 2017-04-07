import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoltageSharedModule } from '../../shared';

import {
    BlogCategoryService,
    BlogCategoryPopupService,
    BlogCategoryComponent,
    BlogCategoryDetailComponent,
    BlogCategoryDialogComponent,
    BlogCategoryPopupComponent,
    BlogCategoryDeletePopupComponent,
    BlogCategoryDeleteDialogComponent,
    blogCategoryRoute,
    blogCategoryPopupRoute,
    BlogCategoryResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...blogCategoryRoute,
    ...blogCategoryPopupRoute,
];

@NgModule({
    imports: [
        VoltageSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BlogCategoryComponent,
        BlogCategoryDetailComponent,
        BlogCategoryDialogComponent,
        BlogCategoryDeleteDialogComponent,
        BlogCategoryPopupComponent,
        BlogCategoryDeletePopupComponent,
    ],
    entryComponents: [
        BlogCategoryComponent,
        BlogCategoryDialogComponent,
        BlogCategoryPopupComponent,
        BlogCategoryDeleteDialogComponent,
        BlogCategoryDeletePopupComponent,
    ],
    providers: [
        BlogCategoryService,
        BlogCategoryPopupService,
        BlogCategoryResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoltageBlogCategoryModule {}
