import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { BlogCategory } from './blog-category.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class BlogCategoryService {

    private resourceUrl = 'api/blog-categories';
    private resourceSearchUrl = 'api/_search/blog-categories';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(blogCategory: BlogCategory): Observable<BlogCategory> {
        let copy: BlogCategory = Object.assign({}, blogCategory);
        copy.created = this.dateUtils.toDate(blogCategory.created);
        copy.updated = this.dateUtils.toDate(blogCategory.updated);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(blogCategory: BlogCategory): Observable<BlogCategory> {
        let copy: BlogCategory = Object.assign({}, blogCategory);

        copy.created = this.dateUtils.toDate(blogCategory.created);

        copy.updated = this.dateUtils.toDate(blogCategory.updated);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<BlogCategory> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            let jsonResponse = res.json();
            jsonResponse.created = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.created);
            jsonResponse.updated = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.updated);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    private convertResponse(res: any): any {
        let jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].created = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].created);
            jsonResponse[i].updated = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].updated);
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
