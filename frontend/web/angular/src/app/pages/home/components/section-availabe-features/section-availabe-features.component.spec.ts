import { TestBed, async, ComponentFixture } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { HttpClientModule } from '@angular/common/http';
import { ToastrModule } from 'ngx-toastr';
import { ApiModule } from 'src/app/generated';
import { getConfiguration } from 'src/app/app.module';
import { SectionAvailabeFeaturesComponent } from './section-availabe-features.component';

describe('Quick should aviable features', () => {

    let component: SectionAvailabeFeaturesComponent;
    let fixture: ComponentFixture<SectionAvailabeFeaturesComponent>

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [SectionAvailabeFeaturesComponent],
            imports: [
                HttpClientModule,
                TranslateModule.forRoot(),
                ToastrModule.forRoot(),
                ApiModule.forRoot(getConfiguration),
            ],
            providers: [
            ],
            schemas: [NO_ERRORS_SCHEMA]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(SectionAvailabeFeaturesComponent);
        component = fixture.debugElement.componentInstance;
        fixture.detectChanges();
    });

    it('should create the section aviable features component', () => {
        expect(component).toBeTruthy();
    });


    it('should add side', () => {
        component.addSlide()
    });

    it('should remove side', () => {
        component.removeSlide();
        expect(component.features.length).toEqual(5);
    });

    it('should others', () => {
        let e: any;
        component.afterChange(e);
        component.breakpoint(e);
        component.slickInit(e);
        component.beforeChange(e);
    });



});
