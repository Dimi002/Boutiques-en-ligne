import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DepartmentsRoutingModule } from './departments-routing.module';
import { SharedComponentsModule } from 'src/app/components/shared-components.module';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpLoaderFactory } from 'src/app/app.module';
import { HttpClient } from '@angular/common/http';
import { HomeModule } from '../home/home.module';
import { IndexComponent } from './components/index/index.component';
import { SectionSideNavComponent } from './components/section-side-nav/section-side-nav.component';
import { SectionDepartmentDescComponent } from './components/section-department-desc/section-department-desc.component';

@NgModule({
  declarations: [
    IndexComponent,
    SectionSideNavComponent,
    SectionDepartmentDescComponent
  ],
  imports: [
    CommonModule,
    SharedComponentsModule,
    DepartmentsRoutingModule,
    TranslateModule.forChild({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    HomeModule
  ]
})
export class DepartmentsModule { }
