import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoltageSharedModule } from '../../shared';

import {
    AppPropertyService,
    AppPropertyPopupService,
    AppPropertyComponent,
    AppPropertyDialogComponent,
    AppPropertyPopupComponent,
    AppPropertyDeletePopupComponent,
    AppPropertyDeleteDialogComponent,
    appPropertyRoute,
    appPropertyPopupRoute,
} from './';

let ENTITY_STATES = [
    ...appPropertyRoute,
    ...appPropertyPopupRoute,
];

@NgModule({
    imports: [
        VoltageSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AppPropertyComponent,
        AppPropertyDialogComponent,
        AppPropertyDeleteDialogComponent,
        AppPropertyPopupComponent,
        AppPropertyDeletePopupComponent,
    ],
    entryComponents: [
        AppPropertyComponent,
        AppPropertyDialogComponent,
        AppPropertyPopupComponent,
        AppPropertyDeleteDialogComponent,
        AppPropertyDeletePopupComponent,
    ],
    providers: [
        AppPropertyService,
        AppPropertyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoltageAppPropertyModule {}
