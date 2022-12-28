import { TestBed, async, ComponentFixture } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { HttpClientModule } from '@angular/common/http';
import { ToastrModule } from 'ngx-toastr';
import { ApiModule, Specialist, SpecialistControllerService } from 'src/app/generated';
import { getConfiguration } from 'src/app/app.module';
import { SectionDoctorsComponent } from './section-doctors.component';
import { LanguageService } from 'src/app/services/language.service';

describe('section director citation', () => {

    let component: SectionDoctorsComponent;
    let fixture: ComponentFixture<SectionDoctorsComponent>

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [SectionDoctorsComponent],
            imports: [
                HttpClientModule,
                TranslateModule.forRoot(),
                ToastrModule.forRoot(),
                ApiModule.forRoot(getConfiguration),
            ],
            providers: [
                SpecialistControllerService,
                LanguageService
            ],
            schemas: [NO_ERRORS_SCHEMA]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(SectionDoctorsComponent);
        component = fixture.debugElement.componentInstance;
        fixture.detectChanges();
    });

    it('should create the section director citation component', () => {
        expect(component).toBeTruthy();
    });

    it('should add side', () => {
        component.addSlide()
    });

    it('should getDatas', () => {
        expect(component.doctors.length).toEqual(0)
        component.getData()
    });

    it('should remove side', () => {
        component.removeSlide();
    });

    it('should others', () => {
        let e: any;
        component.afterChange(e);
        component.breakpoint(e);
        component.slickInit(e);
        component.beforeChange(e);
    });



});
