import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {RouterModule} from '@angular/router';

import {VoltageSharedModule} from '../shared';

import {PUBLIC_BLOG_ROUTE, PublicBlogComponent} from './';
import {PublicBlogCategoryService} from './public-blog-category.service';
import {PublicBlogService} from './public-blog.service';


@NgModule({
    imports: [
        VoltageSharedModule,
        RouterModule.forRoot([...PUBLIC_BLOG_ROUTE], {useHash: true})
    ],
    declarations: [
        PublicBlogComponent,
    ],
    entryComponents: [],
    providers: [
        PublicBlogService, PublicBlogCategoryService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoltagePublicBlogModule {
}
