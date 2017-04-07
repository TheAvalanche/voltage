import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoltageSharedModule } from '../../shared';

import {
    NewsService,
    NewsPopupService,
    NewsComponent,
    NewsDetailComponent,
    NewsDialogComponent,
    NewsPopupComponent,
    NewsDeletePopupComponent,
    NewsDeleteDialogComponent,
    newsRoute,
    newsPopupRoute,
    NewsResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...newsRoute,
    ...newsPopupRoute,
];

@NgModule({
    imports: [
        VoltageSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        NewsComponent,
        NewsDetailComponent,
        NewsDialogComponent,
        NewsDeleteDialogComponent,
        NewsPopupComponent,
        NewsDeletePopupComponent,
    ],
    entryComponents: [
        NewsComponent,
        NewsDialogComponent,
        NewsPopupComponent,
        NewsDeleteDialogComponent,
        NewsDeletePopupComponent,
    ],
    providers: [
        NewsService,
        NewsPopupService,
        NewsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoltageNewsModule {}
