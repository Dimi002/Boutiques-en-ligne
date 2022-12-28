import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AppointmentsRoutingModule } from './appointments-routing.module';
import { IndexComponent } from './components/index/index.component';
import { AppointmentsListComponent } from './components/appointments-list/appointments-list.component';
import { DisplayAppointmentsComponent } from './components/display-appointments/display-appointments.component';
import { DataTablesModule } from 'angular-datatables';
import { HttpClient } from '@angular/common/http';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpLoaderFactory } from 'src/app/configs/http-loader-factory';
import { SharedComponentsModule } from 'src/app/components/shared-components.module';

@NgModule({
  declarations: [
    IndexComponent,
    AppointmentsListComponent, 
    DisplayAppointmentsComponent
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
    DataTablesModule,
    SharedComponentsModule
  ],
  exports: [ DisplayAppointmentsComponent ]
})
export class AppointmentsModule { }
