import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Slide } from './slide.model';
import {DateUtils, JhiLanguageService} from 'ng-jhipster';
@Injectable()
export class SlideService {

    private resourceUrl = 'api/slides';
    private resourceLangUrl = 'api/slides/lang';

    constructor(private http: Http,
                private dateUtils: DateUtils,
                private languageService: JhiLanguageService) { }

    create(slide: Slide): Observable<Slide> {
        let copy: Slide = Object.assign({}, slide);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(slide: Slide): Observable<Slide> {
        let copy: Slide = Object.assign({}, slide);

        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Slide> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    queryByCurrentLanguage(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceLangUrl + '/' + this.languageService.currentLang, options)
            .map((res: any) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }


    private convertResponse(res: any): any {
        res._body = res.json();
        return res;
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        let options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            let params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}
