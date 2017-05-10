import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {RouterModule} from '@angular/router';

import {VoltageSharedModule} from '../shared';

import {PUBLIC_BLOG_DETAIL_ROUTE, PublicBlogDetailComponent} from './';


@NgModule({
    imports: [
        VoltageSharedModule,
        RouterModule.forRoot([PUBLIC_BLOG_DETAIL_ROUTE], {useHash: true})
    ],
    declarations: [
        PublicBlogDetailComponent
    ],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoltagePublicBlogDetailModule {
}
