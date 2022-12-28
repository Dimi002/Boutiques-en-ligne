import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AppointmentsRoutingModule } from './appointments-routing.module';
import { IndexComponent } from './components/index/index.component';
import { DoctorModule } from '../doctor-dashboard/doctor.module';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { HttpLoaderFactory } from 'src/app/configs/http-loader-factory';

@NgModule({
  declarations: [
    IndexComponent
  ],
  imports: [
    CommonModule,
    AppointmentsRoutingModule,
    TranslateModule.forChild({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    DoctorModule
  ]
})
export class AppointmentsModule { }
