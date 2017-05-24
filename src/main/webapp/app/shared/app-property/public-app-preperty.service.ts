import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {JhiLanguageService} from 'ng-jhipster';
import {AppProperty} from '../../entities/app-property/app-property.model';
import {Response} from '@angular/http';

@Injectable()
export class PublicAppPropertyService {

    private resourceLangUrl = 'public/api/app-properties/lang';

    appProperties: AppProperty[];

    constructor(private http: Http,
                private languageService: JhiLanguageService) {
        this.queryByCurrentLanguage().subscribe(
            (res: Response) => {
                this.appProperties = res.json();
            },
            (res: Response) => {
            }
        );
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
