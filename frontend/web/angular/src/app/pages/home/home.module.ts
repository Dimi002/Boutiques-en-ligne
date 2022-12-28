import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './components/index/home.component';

import { SlickCarouselModule } from 'ngx-slick-carousel';
import { SectionAvailabeFeaturesComponent } from './components/section-availabe-features/section-availabe-features.component';
import { SharedComponentsModule } from 'src/app/components/shared-components.module';
import { SectionSpecialitiesComponent } from './components/section-specialities/section-specialities.component';
import { SectionDoctorsComponent } from './components/section-doctors/section-doctors.component';
import { DoctorWidgetComponent } from './components/doctor-widget/doctor-widget.component';
import { HttpClient } from '@angular/common/http';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { SectionSearchComponent } from './components/section-search/section-search.component';
import { SectionQuickAppointmentComponent } from './components/section-quick-appointment/section-quick-appointment.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { QuickAppointmentFormComponent } from './components/quick-appointment-form/quick-appointment-form.component';
import { DoctorImageSliderComponent } from './components/doctor-image-slider/doctor-image-slider.component';
import { SectionVideoComponent } from './components/section-video/section-video.component';
import { SectionTestimonialsComponent } from './components/section-testimonials/section-testimonials.component';
import { TestimonialWidgetComponent } from './components/testimonial-widget/testimonial-widget.component';
import { SectionDirectorCitationComponent } from './components/section-director-citation/section-director-citation.component';
import { SectionMapaDescriptionComponent } from './components/section-mapa-description/section-mapa-description.component';
import { HttpLoaderFactory } from 'src/app/app.module';

@NgModule({
  declarations: [ HomeComponent, SectionAvailabeFeaturesComponent, SectionSpecialitiesComponent, SectionDoctorsComponent, DoctorWidgetComponent, SectionSearchComponent, SectionQuickAppointmentComponent, QuickAppointmentFormComponent, DoctorImageSliderComponent, SectionVideoComponent, SectionTestimonialsComponent, TestimonialWidgetComponent, SectionDirectorCitationComponent, SectionMapaDescriptionComponent ],
  imports: [
    CommonModule,
    FormsModule, 
    ReactiveFormsModule,
    HomeRoutingModule,
    SlickCarouselModule,
    SharedComponentsModule,
    TranslateModule.forChild({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
  ],
  exports: [ SectionDoctorsComponent, SectionDirectorCitationComponent, SectionTestimonialsComponent, SectionMapaDescriptionComponent, SectionSpecialitiesComponent ]
})
export class HomeModule { }
