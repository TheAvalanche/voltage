import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import {JhiLanguageService} from 'ng-jhipster';
import {BlogCategory} from '../entities/blog-category/blog-category.model';

@Injectable()
export class PublicBlogCategoryService {

    private resourceUrl = 'public/api/blog-categories';
    private resourceLangUrl = 'public/api/blog-categories/lang';

    constructor(private http: Http,
                private languageService: JhiLanguageService) { }


    find(id: number): Observable<BlogCategory> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(): Observable<Response> {
        return this.http.get(this.resourceUrl)
            .map((res: any) => this.convertResponse(res));
    }

    queryByCurrentLanguage(): Observable<Response> {
        return this.http.get(this.resourceLangUrl + '/' + this.languageService.currentLang)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: any): any {
        res._body = res.json();
        return res;
    }
}
