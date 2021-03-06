import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Blog } from './blog.model';
import {DateUtils, JhiLanguageService} from 'ng-jhipster';
@Injectable()
export class BlogService {

    private resourceUrl = 'api/blogs';
    private resourceLangUrl = 'api/blogs/lang';

    constructor(private http: Http,
                private dateUtils: DateUtils,
                private languageService: JhiLanguageService) { }

    create(blog: Blog): Observable<Blog> {
        let copy: Blog = Object.assign({}, blog);
        copy.created = this.dateUtils.toDate(blog.created);

        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(blog: Blog): Observable<Blog> {
        let copy: Blog = Object.assign({}, blog);

        copy.created = this.dateUtils.toDate(blog.created);

        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

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

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
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
