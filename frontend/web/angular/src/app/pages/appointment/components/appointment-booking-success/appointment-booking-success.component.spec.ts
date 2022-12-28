import { TestBed } from "@angular/core/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { ReplaySubject } from "rxjs";
import { AuthenticationService } from "src/app/services/authentication.service";
import { LanguageService } from "src/app/services/language.service";
import { AppointmentBookingSuccessComponent } from "./appointment-booking-success.component";

describe('Appointment Booking Success', () => {
    let languageSubject = new ReplaySubject(1);
    const languageService = jasmine.createSpyObj('LanguageService ', { 'get': languageSubject.asObservable(), 'getDeviceLanguage': undefined });
    const authService = jasmine.createSpyObj('AuthenticationService ', ['login', 'register', 'getAppointment']);
    let component: AppointmentBookingSuccessComponent;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [
                RouterTestingModule,
            ],
            declarations: [
                AppointmentBookingSuccessComponent
            ],
            providers: [
                { provide: LanguageService, useValue: languageService },
                { provide: AuthenticationService, useValue: authService }
            ]
        }).compileComponents();
    });
    beforeEach(() => {
        const fixture = TestBed.createComponent(AppointmentBookingSuccessComponent);
        component = fixture.componentInstance;
    })
    it('should create appointment success', () => {
        expect(languageService.get).toHaveBeenCalled();
        expect(component).toBeTruthy();
    });

    it('should get hour when exist string hour', () => {
        const hour = (new Date()).toString();
        const res = component.getHour(hour);
        const expecValue = hour.substring(hour.indexOf('T') + 1);
        expect(res).toEqual(expecValue);
    });

    it('should get hour with empty string hour', () => {
        const res = component.getHour(undefined);
        expect(res).toEqual('');
    });
})