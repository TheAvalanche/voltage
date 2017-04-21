import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { VoltageSharedModule } from '../../shared';

import {PublicNewsComponent} from './public-news.component';
import {PublicNewsService} from './public-news.service';
import {RouterModule} from '@angular/router';


@NgModule({
    imports: [
        VoltageSharedModule,
        RouterModule
    ],
    declarations: [
        PublicNewsComponent
    ],
    exports: [
        PublicNewsComponent
    ],
    entryComponents: [
    ],
    providers: [
        PublicNewsService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoltagePublicNewsModule {}
