import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoltageSharedModule } from '../shared';

import { PUBLIC_BLOG_ROUTE, PublicBlogComponent} from './';


@NgModule({
    imports: [
        VoltageSharedModule,
        RouterModule.forRoot([ PUBLIC_BLOG_ROUTE ], { useHash: true })
    ],
    declarations: [
        PublicBlogComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoltagePublicBlogModule {}
