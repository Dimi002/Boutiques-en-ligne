import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ContactUsRoutingModule } from './contact-us-routing.module';
import { IndexComponent } from './components/index/index.component';
import { SharedComponentsModule } from 'src/app/components/shared-components.module';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpLoaderFactory } from 'src/app/app.module';
import { HttpClient } from '@angular/common/http';
import { HomeModule } from '../home/home.module';
import { SectionContactCardsComponent } from './components/section-contact-cards/section-contact-cards.component';
import { SectionContactFormComponent } from './components/section-contact-form/section-contact-form.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    IndexComponent,
    SectionContactCardsComponent,
    SectionContactFormComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    SharedComponentsModule,
    ContactUsRoutingModule,
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
export class ContactUsModule { }
