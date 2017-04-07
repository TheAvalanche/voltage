import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { VoltageTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { NewsDetailComponent } from '../../../../../../main/webapp/app/entities/news/news-detail.component';
import { NewsService } from '../../../../../../main/webapp/app/entities/news/news.service';
import { News } from '../../../../../../main/webapp/app/entities/news/news.model';

describe('Component Tests', () => {

    describe('News Management Detail Component', () => {
        let comp: NewsDetailComponent;
        let fixture: ComponentFixture<NewsDetailComponent>;
        let service: NewsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoltageTestModule],
                declarations: [NewsDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    NewsService,
                    EventManager
                ]
            }).overrideComponent(NewsDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NewsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NewsService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new News(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.news).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
