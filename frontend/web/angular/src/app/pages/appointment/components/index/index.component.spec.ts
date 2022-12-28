import { TestBed } from "@angular/core/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { ReplaySubject } from "rxjs";
import { SpecialistControllerService, SpecialistSpecialityControllerService, SpecialityControllerService } from "src/app/generated";
import { AuthenticationService } from "src/app/services/authentication.service";
import { DateParserService } from "src/app/services/date-parser.service";
import { LanguageService } from "src/app/services/language.service";
import { NotificationService } from "src/app/services/notification.service";
import { IndexComponent } from "./index.component";

describe('Appointment Index Component', () => {
    let languageSubject = new ReplaySubject(1);
    const languageService = jasmine.createSpyObj('LanguageService ', { 'get': languageSubject.asObservable(), 'getDeviceLanguage': undefined });
    const authService = jasmine.createSpyObj('AuthenticationService ', ['login', 'register', 'getAppointment']);
    const specialityControllerService = jasmine.createSpyObj('SpecialityControllerService ', ['findAllSpecialitiesMinUsingGET']);
    const specialistControllerService = jasmine.createSpyObj('SpecialistControllerService ', ['findSpecialistByIdUsingGET']);
    const specialistSpecialityControllerService = jasmine.createSpyObj('SpecialistSpecialityControllerService ', ['getAllSpecialitySpecialistsByIdUsingGET']);
    const dateParserService = jasmine.createSpyObj('DateParserService ', ['formatYYYYMMDDDate', 'setMinutesTo00or30']);
    const notificationService = jasmine.createSpyObj('NotificationService ', ['danger']);
    let component: IndexComponent;
    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [
                RouterTestingModule,
            ],
            declarations: [
                IndexComponent
            ],
            providers: [
                { provide: LanguageService, useValue: languageService },
                { provide: AuthenticationService, useValue: authService },
                { provide: SpecialityControllerService, useValue: specialityControllerService },
                { provide: SpecialistControllerService, useValue: specialistControllerService },
                { provide: SpecialistSpecialityControllerService, useValue: specialistSpecialityControllerService },
                { provide: DateParserService, useValue: dateParserService },
                { provide: NotificationService, useValue: notificationService }
            ]
        }).compileComponents();
    });

    it('should create appointment index component', () => {
        const fixture = TestBed.createComponent(IndexComponent);
        component = fixture.componentInstance;
        expect(component).toBeTruthy();
    });

})