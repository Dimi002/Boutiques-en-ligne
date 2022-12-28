import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { IndexComponent } from './components/index/index.component';

import { SlickCarouselModule } from 'ngx-slick-carousel';
import { SharedComponentsModule } from 'src/app/components/shared-components.module';
import { HttpClient } from '@angular/common/http';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { DashboardTotalComponent } from './components/dashboard-total/dashboard-total.component';
import { AppointmentsModule } from '../appointments/appointments.module';
import { DoctorsModule } from '../doctors/doctors.module';
import { PatientsModule } from '../patients/patients.module';
import { HttpLoaderFactory } from 'src/app/configs/http-loader-factory';
import { ContactsModule } from '../contacts/contacts.module';

@NgModule({
  declarations: [ IndexComponent, DashboardTotalComponent ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    SlickCarouselModule,
    SharedComponentsModule,
    TranslateModule.forChild({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    AppointmentsModule,
    ContactsModule,
    DoctorsModule,
    PatientsModule
  ]
})
export class AdminModule { }
