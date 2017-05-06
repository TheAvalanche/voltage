import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import {DateUtils, JhiLanguageService} from 'ng-jhipster';
import {Blog} from '../entities/blog/blog.model';

@Injectable()
export class PublicBlogService {

    private resourceUrl = 'public/api/blogs';
    private resourceLangUrl = 'public/api/blogs/lang';
    private resourceCategoryUrl = 'public/api/blogs/category';

    constructor(private http: Http,
                private dateUtils: DateUtils,
                private languageService: JhiLanguageService) { }

    find(id: number): Observable<Blog> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            let jsonResponse = res.json();
            jsonResponse.created = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.created);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    queryByCurrentLanguage(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceLangUrl + '/' + this.languageService.currentLang, options)
            .map((res: any) => this.convertResponse(res));
    }

    queryByCategory(id: number, req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceCategoryUrl + '/' + id, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: any): any {
        let jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].created = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].created);
        }
        res._body = jsonResponse;
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
