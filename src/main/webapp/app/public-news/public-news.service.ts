import {Injectable} from '@angular/core';
import {Http, Response, URLSearchParams, BaseRequestOptions} from '@angular/http';
import {Observable} from 'rxjs/Rx';


import {DateUtils, JhiLanguageService} from 'ng-jhipster';
import {News} from '../entities/news/news.model';

@Injectable()
export class PublicNewsService {

    private resourceUrl = 'public/api/news';
    private resourceLangUrl = 'public/api/news/lang';

    constructor(private http: Http,
                private dateUtils: DateUtils,
                private languageService: JhiLanguageService) {
    }

    find(id: number): Observable<News> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            let jsonResponse = res.json();
            jsonResponse.created = this.dateUtils.convertDateTimeFromServer(jsonResponse.created);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options).map((res: any) => this.convertResponse(res));
    }

    queryByCurrentLanguage(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceLangUrl + '/' + this.languageService.currentLang, options)
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
