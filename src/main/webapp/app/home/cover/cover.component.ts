import {Component, OnInit} from '@angular/core';
import {Slide} from '../../entities/slide/slide.model';
import {PublicSlideService} from './public-slide.service';
import {AlertService} from 'ng-jhipster';
import {Response} from '@angular/http';

@Component({
    selector: 'jhi-cover',
    templateUrl: './cover.component.html',
    styleUrls: [
        'cover.scss'
    ]
})
export class CoverComponent implements OnInit {
    slides: Slide[];


    constructor(private alertService: AlertService,
                private publicSlideService: PublicSlideService) {

    }

    ngOnInit(): void {
        this.loadAllSlides();
    }

    loadAllSlides() {
        this.publicSlideService.queryByCurrentLanguage().subscribe(
            (res: Response) => this.onSuccess(res.json()),
            (res: Response) => this.onError(res.json())
        );
    }

    private onSuccess(data) {
        this.slides = data;
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
