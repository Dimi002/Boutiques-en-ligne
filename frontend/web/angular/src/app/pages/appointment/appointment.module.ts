import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AppointmentRoutingModule } from './appointment-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SlickCarouselModule } from 'ngx-slick-carousel';
import { SharedComponentsModule } from 'src/app/components/shared-components.module';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { ModalModule } from 'ngx-bootstrap/modal';
import { HttpClient } from '@angular/common/http';
import { HttpLoaderFactory } from 'src/app/app.module';
import { IndexComponent } from './components/index/index.component';
import { SectionSpecialitiesHeaderComponent } from './components/section-specialities-header/section-specialities-header.component';
import { SectionDoctorsSliderComponent } from './components/section-doctors-slider/section-doctors-slider.component';
import { DoctorsSliderWidgetComponent } from './components/doctors-slider-widget/doctors-slider-widget.component';
import { SectionDoctorDetailsCardComponent } from './components/section-doctor-details-card/section-doctor-details-card.component';
import { ScheduleWidgetComponent } from './components/schedule-widget/schedule-widget.component';
import { AppointmentFormComponent } from './components/appointment-form/appointment-form.component';
import { AppointmentBookingSuccessComponent } from './components/appointment-booking-success/appointment-booking-success.component';
import { AppointmentBookingVerifyComponent } from './components/appointment-booking-verify/appointment-booking-verify.component';


@NgModule({
  declarations: [
    IndexComponent,
    SectionSpecialitiesHeaderComponent,
    SectionDoctorsSliderComponent,
    DoctorsSliderWidgetComponent,
    SectionDoctorDetailsCardComponent,
    ScheduleWidgetComponent,
    AppointmentFormComponent,
    AppointmentBookingSuccessComponent,
    AppointmentBookingVerifyComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    AppointmentRoutingModule,
    SlickCarouselModule,
    SharedComponentsModule,
    ModalModule.forRoot(),
    TranslateModule.forChild({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
  ], schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppointmentModule { }
