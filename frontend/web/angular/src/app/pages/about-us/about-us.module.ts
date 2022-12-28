import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AboutUsRoutingModule } from './about-us-routing.module';
import { IndexComponent } from './components/index/index.component';
import { SharedComponentsModule } from 'src/app/components/shared-components.module';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpLoaderFactory } from 'src/app/app.module';
import { HttpClient } from '@angular/common/http';
import { HomeModule } from '../home/home.module';
import { SectionAboutDetailComponent } from './components/section-about-detail/section-about-detail.component';
import { GalerieModule } from '../gallery/galerie.module';


@NgModule({
  declarations: [
    IndexComponent,
    SectionAboutDetailComponent
  ],
  imports: [
    CommonModule,
    AboutUsRoutingModule,
    SharedComponentsModule,
    TranslateModule.forChild({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    HomeModule,
    GalerieModule
  ]
})
export class AboutUsModule { }
