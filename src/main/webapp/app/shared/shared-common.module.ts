import {NgModule, Sanitizer} from '@angular/core';
import {Title} from '@angular/platform-browser';

import {TranslateService} from 'ng2-translate';
import {AlertService} from 'ng-jhipster';


import {
    VoltageSharedLibsModule,
    JhiLanguageHelper,
    FindLanguageFromKeyPipe,
    JhiAlertComponent,
    JhiAlertErrorComponent
} from './';
import {JhiTinyComponent} from './tinymce/tiny.component';
import {SliceParagraphPipe} from './pipe/slice-paragraph.pipe';


export function alertServiceProvider(sanitizer: Sanitizer, translateService: TranslateService) {
    // set below to true to make alerts look like toast
    let isToast = false;
    return new AlertService(sanitizer, isToast, translateService);
}

@NgModule({
    imports: [
        VoltageSharedLibsModule
    ],
    declarations: [
        FindLanguageFromKeyPipe,
        JhiAlertComponent,
        JhiAlertErrorComponent,
        JhiTinyComponent,
        SliceParagraphPipe
    ],
    providers: [
        JhiLanguageHelper,
        {
            provide: AlertService,
            useFactory: alertServiceProvider,
            deps: [Sanitizer, TranslateService]
        },
        Title
    ],
    exports: [
        VoltageSharedLibsModule,
        FindLanguageFromKeyPipe,
        JhiAlertComponent,
        JhiAlertErrorComponent,
        JhiTinyComponent,
        SliceParagraphPipe
    ]
})
export class VoltageSharedCommonModule {
}
