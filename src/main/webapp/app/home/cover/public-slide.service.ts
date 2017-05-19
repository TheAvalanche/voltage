import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import {DateUtils, JhiLanguageService} from 'ng-jhipster';
@Injectable()
export class PublicSlideService {

    private resourceUrl = 'public/api/slides';
    private resourceLangUrl = 'public/api/slides/lang';

    constructor(private http: Http,
                private dateUtils: DateUtils,
                private languageService: JhiLanguageService) { }


    queryByCurrentLanguage(): Observable<Response> {
        return this.http.get(this.resourceLangUrl + '/' + this.languageService.currentLang)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: any): any {
        res._body = res.json();
        return res;
    }
}
