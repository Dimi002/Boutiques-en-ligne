import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoginRoutingModule } from './login-routing.module';
import { LoginFormComponent } from './components/login-form/login-form.component';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { HttpLoaderFactory } from 'src/app/configs/http-loader-factory';
import { SharedComponentsModule } from 'src/app/components/shared-components.module';
import { EmailResetPasswordModalComponent } from 'src/app/components/email-reset-password-modal/email-reset-password-modal.component';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    LoginFormComponent, EmailResetPasswordModalComponent
  ],
  imports: [
    CommonModule,
    LoginRoutingModule,
    TranslateModule.forChild({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    FormsModule,
    ReactiveFormsModule,
    SharedComponentsModule
  ]
})
export class LoginModule { }
