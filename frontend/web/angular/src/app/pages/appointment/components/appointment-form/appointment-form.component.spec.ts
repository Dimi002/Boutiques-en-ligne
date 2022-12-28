import { TestBed } from "@angular/core/testing";
import { ReactiveFormsModule } from "@angular/forms";
import { RouterTestingModule } from "@angular/router/testing";
import { ReplaySubject } from "rxjs";
import { LanguageService } from "src/app/services/language.service";
import { AppointmentFormComponent } from "./appointment-form.component";

describe('Appointment Form Component', () => {
    let languageSubject = new ReplaySubject(1);
    const languageService = jasmine.createSpyObj('LanguageService ', { 'get': languageSubject.asObservable(), 'getDeviceLanguage': undefined });
    let component: AppointmentFormComponent;
    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [
                RouterTestingModule,
                ReactiveFormsModule
            ],
            declarations: [
                AppointmentFormComponent
            ],
            providers: [
                { provide: LanguageService, useValue: languageService },
            ]
        }).compileComponents();
    });

    beforeEach(() => {
        const fixture = TestBed.createComponent(AppointmentFormComponent);
        component = fixture.componentInstance;
    })

    it('should create appointment form', () => {
        expect(component).toBeTruthy();
    });

    it('should translate verify', () => {
        component.getTranslations();
        expect(languageService.get).toHaveBeenCalledTimes(9);
    });

    it('should save change', () => {
        spyOn(component.save, 'emit');
        component.saveChanges();
        expect(component.save.emit).toHaveBeenCalledTimes(1);
    });


})