import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoltageSharedModule } from '../../shared';

import {
    SlideService,
    SlidePopupService,
    SlideComponent,
    SlideDialogComponent,
    SlidePopupComponent,
    SlideDeletePopupComponent,
    SlideDeleteDialogComponent,
    slideRoute,
    slidePopupRoute,
} from './';

let ENTITY_STATES = [
    ...slideRoute,
    ...slidePopupRoute,
];

@NgModule({
    imports: [
        VoltageSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SlideComponent,
        SlideDialogComponent,
        SlideDeleteDialogComponent,
        SlidePopupComponent,
        SlideDeletePopupComponent,
    ],
    entryComponents: [
        SlideComponent,
        SlideDialogComponent,
        SlidePopupComponent,
        SlideDeleteDialogComponent,
        SlideDeletePopupComponent,
    ],
    providers: [
        SlideService,
        SlidePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoltageSlideModule {}
