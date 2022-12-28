import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SettingsRoutingModule } from './settings-routing.module';
import { SharedComponentsModule } from 'src/app/components/shared-components.module';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpLoaderFactory } from 'src/app/configs/http-loader-factory';
import { HttpClient } from '@angular/common/http';
import { ModalModule } from 'ngx-bootstrap/modal';
import { SettingsFormComponent } from './components/settings-form/settings-form.component';



@NgModule({
  declarations: [
    SettingsFormComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    SettingsRoutingModule,
    SharedComponentsModule,
    TranslateModule.forChild({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    ModalModule.forRoot(),
  ]
})
export class SettingsModule { }
