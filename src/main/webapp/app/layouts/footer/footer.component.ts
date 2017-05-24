import {Component, OnInit} from '@angular/core';
import {PublicAppPropertyService} from '../../shared/app-property/public-app-preperty.service';
import {AppProperty} from '../../entities/app-property/app-property.model';
import {Response} from '@angular/http';

@Component({
    selector: 'jhi-footer',
    templateUrl: './footer.component.html',
    styleUrls: [
        'footer.scss'
    ]
})
export class FooterComponent implements OnInit {
    appProperties: AppProperty[];
    appPropertiesMap: Map<String, String>;

    constructor(private appPropertiesService: PublicAppPropertyService) {
        this.appPropertiesMap = new Map();
    }


    ngOnInit(): void {
        this.appPropertiesService.queryByCurrentLanguage().subscribe(
            (res: Response) => {
                this.appProperties = res.json();
                for (let property of this.appProperties) {
                    this.appPropertiesMap.set(property.name, property.value);
                }
            }
        );
    }
}
