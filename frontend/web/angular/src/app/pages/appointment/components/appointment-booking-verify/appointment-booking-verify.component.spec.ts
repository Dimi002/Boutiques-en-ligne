import { Component, EventEmitter, Input, NO_ERRORS_SCHEMA, Output } from "@angular/core";
import { TestBed } from "@angular/core/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { ReplaySubject } from "rxjs";
import { AppointmentControllerService } from "src/app/generated";
import { AuthenticationService } from "src/app/services/authentication.service";
import { LanguageService } from "src/app/services/language.service";
import { NotificationService } from "src/app/services/notification.service";
import { DoctorsSliderWidgetComponent } from "../doctors-slider-widget/doctors-slider-widget.component";
import { AppointmentBookingVerifyComponent } from "./appointment-booking-verify.component";

describe('Appointment Booking Verify', () => {
    let subject = new ReplaySubject(1);
    const languageService = jasmine.createSpyObj('LanguageService ', { 'get': subject.asObservable(), 'getDeviceLanguage': undefined });
    const authService = jasmine.createSpyObj('AuthenticationService ', ['login', 'register', 'getAppointment']);
    const appointementService = jasmine.createSpyObj('AppointmentControllerService ', { 'createAppointmentUsingPOST': subject.asObservable() });
    const notificationService = jasmine.createSpyObj('NotificationService ', ['success', 'danger']);
    let component: AppointmentBookingVerifyComponent;

    @Component({
        selector: 'app-doctors-slider-widget',
        template: ''
    })
    class DoctorsSliderWidgetComponent {
        @Input() data!: any;
        @Input() showDetailButton: boolean = true;
        @Output() doctorSelect: EventEmitter<any> = new EventEmitter<any>();
    }

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [
                RouterTestingModule,
            ],
            declarations: [
                AppointmentBookingVerifyComponent,
                DoctorsSliderWidgetComponent
            ],
            providers: [
                { provide: LanguageService, useValue: languageService },
                { provide: AuthenticationService, useValue: authService },
                { provide: AppointmentControllerService, useValue: appointementService },
                { provide: NotificationService, useValue: notificationService }
            ],
            schemas: [NO_ERRORS_SCHEMA]
        }).compileComponents();
    });

    beforeEach(() => {
        const fixture = TestBed.createComponent(AppointmentBookingVerifyComponent);
        component = fixture.componentInstance;
    })

    it('should create appointment booking verify', () => {
        expect(component).toBeTruthy();
    });

    it('should translate verify', () => {
        component.getTranslations();
        expect(languageService.get).toHaveBeenCalledTimes(8);
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

    it("Testing create appointment if appointment isn't null", () => {
        component.appointment = {};
        component.saveChanges();
        expect(appointementService.createAppointmentUsingPOST).toHaveBeenCalledTimes(1);
    });
})
