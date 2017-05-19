import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {RouterModule} from '@angular/router';

import {VoltageSharedModule} from '../shared';

import {HOME_ROUTE, HomeComponent} from './';
import {CoverComponent} from './cover/cover.component';
import {VoltagePublicNewsModule} from '../public-news/public-news.module';
import {PublicSlideService} from './cover/public-slide.service';


@NgModule({
    imports: [
        VoltageSharedModule,
        VoltagePublicNewsModule,
        RouterModule.forRoot([HOME_ROUTE], {useHash: true})
    ],
    declarations: [
        HomeComponent, CoverComponent
    ],
    providers: [
        PublicSlideService
    ],
    entryComponents: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoltageHomeModule {
}
