import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { VoltageTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BlogCategoryDetailComponent } from '../../../../../../main/webapp/app/entities/blog-category/blog-category-detail.component';
import { BlogCategoryService } from '../../../../../../main/webapp/app/entities/blog-category/blog-category.service';
import { BlogCategory } from '../../../../../../main/webapp/app/entities/blog-category/blog-category.model';

describe('Component Tests', () => {

    describe('BlogCategory Management Detail Component', () => {
        let comp: BlogCategoryDetailComponent;
        let fixture: ComponentFixture<BlogCategoryDetailComponent>;
        let service: BlogCategoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoltageTestModule],
                declarations: [BlogCategoryDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BlogCategoryService,
                    EventManager
                ]
            }).overrideComponent(BlogCategoryDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BlogCategoryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BlogCategoryService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BlogCategory(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.blogCategory).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
