import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {RouterModule} from '@angular/router';

import {VoltageSharedModule} from '../shared';

import {HOME_ROUTE, HomeComponent} from './';

import {VoltagePublicNewsModule} from '../public-news/public-news.module';
import {PublicSlideService} from './public-slide/public-slide.service';
import {PublicSlideComponent} from './public-slide/public-slide.component';



@NgModule({
    imports: [
        VoltageSharedModule,
        VoltagePublicNewsModule,
        RouterModule.forRoot([HOME_ROUTE], {useHash: true})
    ],
    declarations: [
        HomeComponent, PublicSlideComponent
    ],
    providers: [
        PublicSlideService
    ],
    entryComponents: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoltageHomeModule {
}
