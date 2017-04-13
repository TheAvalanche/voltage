import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoltageSharedModule } from '../shared';

import { HOME_ROUTE, HomeComponent } from './';
import {PublicNewsComponent} from './public-news/public-news.component';
import {PublicNewsService} from './public-news/public-news.service';


@NgModule({
    imports: [
        VoltageSharedModule,
        RouterModule.forRoot([ HOME_ROUTE ], { useHash: true })
    ],
    declarations: [
        HomeComponent, PublicNewsComponent
    ],
    entryComponents: [
    ],
    providers: [
        PublicNewsService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoltageHomeModule {}
