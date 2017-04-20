import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoltageSharedModule } from '../../shared';

import { PUBLIC_NEWS_DETAIL_ROUTE, PublicNewsDetailComponent} from './';


@NgModule({
    imports: [
        VoltageSharedModule,
        RouterModule.forRoot([ PUBLIC_NEWS_DETAIL_ROUTE ], { useHash: true })
    ],
    declarations: [
        PublicNewsDetailComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoltagePublicNewsDetailModule {}
