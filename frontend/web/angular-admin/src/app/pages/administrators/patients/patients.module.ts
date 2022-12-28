import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PatientsRoutingModule } from './patients-routing.module';
import { IndexComponent } from './components/index/index.component';
import { PatientsListComponent } from './components/patients-list/patients-list.component';
import { DisplayPatientsComponent } from './components/display-patients/display-patients.component';
import { DataTablesModule } from 'angular-datatables';
import { HttpClient } from '@angular/common/http';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpLoaderFactory } from 'src/app/configs/http-loader-factory';

@NgModule({
  declarations: [
    IndexComponent,
    PatientsListComponent,
    DisplayPatientsComponent
  ],
  imports: [
    CommonModule,
    PatientsRoutingModule,
    TranslateModule.forChild({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    DataTablesModule
  ],
  exports: [ DisplayPatientsComponent ]
})
export class PatientsModule { }
