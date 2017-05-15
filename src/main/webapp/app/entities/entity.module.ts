import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { VoltageNewsModule } from './news/news.module';
import { VoltageBlogModule } from './blog/blog.module';
import { VoltageBlogCategoryModule } from './blog-category/blog-category.module';
import { VoltageAppPropertyModule } from './app-property/app-property.module';
import { VoltageSlideModule } from './slide/slide.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        VoltageNewsModule,
        VoltageBlogModule,
        VoltageBlogCategoryModule,
        VoltageAppPropertyModule,
        VoltageSlideModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoltageEntityModule {}
