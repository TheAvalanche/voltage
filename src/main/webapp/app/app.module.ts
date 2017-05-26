import './vendor.ts';

import {NgModule, APP_INITIALIZER} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {Ng2Webstorage} from 'ng2-webstorage';

import {VoltageSharedModule, UserRouteAccessService} from './shared';
import {VoltageHomeModule} from './home/home.module';
import {VoltageAdminModule} from './admin/admin.module';
import {VoltageAccountModule} from './account/account.module';
import {VoltageEntityModule} from './entities/entity.module';
import {VoltagePublicBlogModule} from './public-blog/public-blog.module';
import {VoltagePublicNewsDetailModule} from './public-news/public-news-detail.module';

import {LayoutRoutingModule} from './layouts';
import {customHttpProvider} from './blocks/interceptor/http.provider';
import {PaginationConfig} from './blocks/config/uib-pagination.config';

import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';
import {VoltagePublicNewsModule} from './public-news/public-news.module';
import {VoltagePublicBlogDetailModule} from './public-blog/public-blog-detail.module';
import {PublicAppPropertyLoader, PublicAppPropertyService} from './shared/app-property/public-app-preperty.service';


@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({prefix: 'jhi', separator: '-'}),
        VoltageSharedModule,
        VoltageHomeModule,
        VoltageAdminModule,
        VoltageAccountModule,
        VoltageEntityModule,
        VoltagePublicBlogModule,
        VoltagePublicBlogDetailModule,
        VoltagePublicNewsModule,
        VoltagePublicNewsDetailModule
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService,
        PublicAppPropertyService, {
            provide: APP_INITIALIZER,
            useFactory: PublicAppPropertyLoader,
            deps: [PublicAppPropertyService],
            multi: true
        }
    ],
    bootstrap: [JhiMainComponent]
})
export class VoltageAppModule {
}
