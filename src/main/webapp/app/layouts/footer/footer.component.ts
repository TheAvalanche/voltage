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
export class FooterComponent {

    config: any;

    appPropertiesMap: Map<String, String>;

    constructor(private appPropertiesService: PublicAppPropertyService) {
        this.appPropertiesMap = this.appPropertiesService.appPropertiesMap;
    }


}
