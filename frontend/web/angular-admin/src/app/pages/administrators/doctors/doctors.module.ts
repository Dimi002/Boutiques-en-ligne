import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DoctorsRoutingModule } from './doctors-routing.module';
import { IndexComponent } from './components/index/index.component';
import { DoctorsListComponent } from './components/doctors-list/doctors-list.component';
import { DisplayDoctorsComponent } from './components/display-doctors/display-doctors.component';
import { DataTablesModule } from 'angular-datatables';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { HttpLoaderFactory } from 'src/app/configs/http-loader-factory';
import { SharedComponentsModule } from 'src/app/components/shared-components.module';

@NgModule({
  declarations: [
    IndexComponent,
    DoctorsListComponent,
    DisplayDoctorsComponent
  ],
  imports: [
    CommonModule,
    DoctorsRoutingModule,
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
  exports: [ DisplayDoctorsComponent ]
})
export class DoctorsModule { }
