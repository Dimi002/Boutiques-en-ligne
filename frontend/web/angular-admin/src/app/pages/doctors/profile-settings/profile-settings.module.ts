import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProfileSettingsRoutingModule } from './profile-settings-routing.module';
import { IndexComponent } from './components/index/index.component';
import { ProfileSettingsFormComponent } from './components/profile-settings-form/profile-settings-form.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedComponentsModule } from 'src/app/components/shared-components.module';
import { ModalModule } from 'ngx-bootstrap/modal';
import { EducationFormComponent } from './components/education-form/education-form.component';
import { ExperienceFormComponent } from './components/experience-form/experience-form.component';
import { AwardsFormComponent } from './components/awards-form/awards-form.component';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { HttpLoaderFactory } from 'src/app/configs/http-loader-factory';

@NgModule({
  declarations: [
    IndexComponent,
    ProfileSettingsFormComponent,
    EducationFormComponent,
    ExperienceFormComponent,
    AwardsFormComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ProfileSettingsRoutingModule,
    SharedComponentsModule,
    ModalModule.forRoot(),
    TranslateModule.forChild({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
  ]
})
export class ProfileSettingsModule { }
