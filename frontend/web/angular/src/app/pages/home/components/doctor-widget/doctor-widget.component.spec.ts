import { TestBed, async, ComponentFixture } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { DoctorWidgetComponent } from './doctor-widget.component';
import { Specialist } from 'src/app/generated';
import { ImageService } from 'src/app/services/image.service';
import { LanguageService } from 'src/app/services/language.service';
import { DateParserService } from 'src/app/services/date-parser.service';
import { NavigationService } from 'src/app/services/navigation.service';
import { TranslateModule } from '@ngx-translate/core';
import { RouterModule } from '@angular/router';

describe('Doctor Widget  Component', () => {

    let component: DoctorWidgetComponent;
    let fixture: ComponentFixture<DoctorWidgetComponent>

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [
                RouterModule,
                RouterTestingModule,
                TranslateModule.forRoot(),
            ],
            declarations: [
                DoctorWidgetComponent
            ],
            providers: [
                ImageService,
                DateParserService,
                LanguageService,
                NavigationService,
            ],
            schemas: [NO_ERRORS_SCHEMA]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(DoctorWidgetComponent);
        component = fixture.debugElement.componentInstance;
        // component.doctors = doctors;
        fixture.detectChanges();
    });

    it('should create the doctor-image-slider component', () => {
        expect(component).toBeTruthy();
    });


    it('should get the gender', () => {

        const gender: string = 'M';
        const res: string = 'Man';
        expect(component.getGender(gender)).toEqual(res)
        const female: string = 'F';
        const fem: string = 'Woman';
        expect(component.getGender(female)).toEqual(fem)
    });

    it('should get specialities', () => {
        const specialities: string[] = ['Medecins', 'infimiers', 'docteur'];
        const specialitiess: string = specialities.join(', ');
        expect(component.getSpecialities(specialities)).toEqual(specialitiess)
        const specialitiest: string[] = ['Medecins', 'infimiers', 'docteur', 'bar'];
        const specialitiesst: string = specialitiest.slice(0, 3).join(', ') + '...';
        expect(component.getSpecialities(specialitiest)).toEqual(specialitiesst)

    });

});
