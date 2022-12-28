import { TestBed, async, ComponentFixture } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { HttpClientModule } from '@angular/common/http';
import { ToastrModule } from 'ngx-toastr';
import { ApiModule, Specialist, SpecialistControllerService } from 'src/app/generated';
import { getConfiguration } from 'src/app/app.module';
import { LanguageService } from 'src/app/services/language.service';
import { SectionMapaDescriptionComponent } from './section-mapa-description.component';

describe('section mapa description', () => {

    let component: SectionMapaDescriptionComponent;
    let fixture: ComponentFixture<SectionMapaDescriptionComponent>

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [SectionMapaDescriptionComponent],
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
        fixture = TestBed.createComponent(SectionMapaDescriptionComponent);
        component = fixture.debugElement.componentInstance;
        fixture.detectChanges();
    });

    it('should create the section mapa descriptioncomponent', () => {
        expect(component).toBeTruthy();
    });


});
