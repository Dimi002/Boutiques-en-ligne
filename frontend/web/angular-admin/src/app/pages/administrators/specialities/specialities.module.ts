import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SpecialitiesRoutingModule } from './specialities-routing.module';
import { IndexComponent } from './components/index/index.component';
import { SpecialitiesListComponent } from './components/specialities-list/specialities-list.component';
import { DisplaySpecialitiesComponent } from './components/display-specialities/display-specialities.component';
import { DataTablesModule } from 'angular-datatables';
import { HttpClient } from '@angular/common/http';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpLoaderFactory } from 'src/app/configs/http-loader-factory';
import { CreateUpdateSpecialityModalComponent } from './components/create-update-speciality-modal/create-update-speciality-modal.component';
import { SharedComponentsModule } from 'src/app/components/shared-components.module';


@NgModule({
  declarations: [
    IndexComponent,
    SpecialitiesListComponent,
    DisplaySpecialitiesComponent,
    CreateUpdateSpecialityModalComponent,
  ],
  imports: [
    CommonModule,
    SpecialitiesRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule.forChild({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    DataTablesModule,
    SharedComponentsModule,
  ]
})
export class SpecialitiesModule { }
