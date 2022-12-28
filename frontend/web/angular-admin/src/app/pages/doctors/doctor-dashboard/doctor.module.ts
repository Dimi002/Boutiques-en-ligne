import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DoctorRoutingModule } from './doctor-routing.module';
import { IndexComponent } from './components/index/index.component';

import { SlickCarouselModule } from 'ngx-slick-carousel';
import { SharedComponentsModule } from 'src/app/components/shared-components.module';
import { HttpClient } from '@angular/common/http';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { ProfileTotalComponent } from './components/profile-total/profile-total.component';
import { AppointmentsListComponent } from './components/appointments-list/appointments-list.component';
import { DisplayAppointmentsComponent } from './components/display-appointments/display-appointments.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { DataTablesModule } from 'angular-datatables';
import { ProfileTotalBaseComponent } from './components/profile-total/profile-total-base.component';
import { HttpLoaderFactory } from 'src/app/configs/http-loader-factory';
import { AppointmentControllerService } from 'src/app/generated';
import { DetailsAppointmentComponent } from './components/details-appointment/details-appointment.component';

@NgModule({
  declarations: [IndexComponent, ProfileTotalBaseComponent, ProfileTotalComponent, AppointmentsListComponent, DisplayAppointmentsComponent, DetailsAppointmentComponent],
  imports: [
    CommonModule,
    DoctorRoutingModule,
    SlickCarouselModule,
    SharedComponentsModule,
    TranslateModule.forChild({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    NgbModule,
    DataTablesModule
  ],
  exports: [AppointmentsListComponent],
  providers: [AppointmentControllerService]
})
export class DoctorModule { }
