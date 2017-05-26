import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {JhiLanguageService} from 'ng-jhipster';

@Injectable()
export class PublicAppPropertyService {

    appPropertiesMap: Map<String, String> = new Map();

    private resourceLangUrl = 'public/api/app-properties/lang';

    constructor(private http: Http,
                private languageService: JhiLanguageService) {
    }

    load() {
        return new Promise((resolve) => {
            this.http.get(this.resourceLangUrl + '/' + this.languageService.currentLang).map(res => res.json())
                .subscribe(
                    properties => {
                        for (let property of properties) {
                            this.appPropertiesMap.set(property.name, property.value);
                        }
                        resolve();
                    }
                );
        });
    }

}
