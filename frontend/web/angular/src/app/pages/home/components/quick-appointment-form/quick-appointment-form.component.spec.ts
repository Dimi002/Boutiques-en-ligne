import { TestBed, async, ComponentFixture } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ImageService } from 'src/app/services/image.service';
import { LanguageService } from 'src/app/services/language.service';
import { DateParserService } from 'src/app/services/date-parser.service';
import { NavigationService } from 'src/app/services/navigation.service';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { RouterModule } from '@angular/router';
import { QuickAppointmentFormComponent } from './quick-appointment-form.component';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { NotificationService } from 'src/app/services/notification.service';
import { FormBuilder } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ToastrModule } from 'ngx-toastr';
import { ApiModule } from 'src/app/generated';
import { getConfiguration } from 'src/app/app.module';

describe('Quick Appointment form Component', () => {

    let component: QuickAppointmentFormComponent;
    let fixture: ComponentFixture<QuickAppointmentFormComponent>

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [QuickAppointmentFormComponent],
            imports: [
                HttpClientModule,
                TranslateModule.forRoot(),
                ToastrModule.forRoot(),
                ApiModule.forRoot(getConfiguration),
            ],
            providers: [
                NavigationService,
                AuthenticationService,
                NotificationService,
                DateParserService,
                LanguageService,
                TranslateService,
                FormBuilder
            ],
            schemas: [NO_ERRORS_SCHEMA]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(QuickAppointmentFormComponent);
        component = fixture.debugElement.componentInstance;
        // component.doctors = doctors;
        fixture.detectChanges();
    });

    it('should create the doctor-image-slider component', () => {
        expect(component).toBeTruthy();
    });


    it('should intit form', () => {
        component.initForm();
    });

    it('should save change', () => {
        component.saveChanges()
    });



});
