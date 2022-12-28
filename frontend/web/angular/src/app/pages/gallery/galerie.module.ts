import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HttpLoaderFactory } from 'src/app/app.module';
import { IndexComponent } from './components/index/index.component';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { SlickCarouselModule } from 'ngx-slick-carousel';
import { SharedComponentsModule } from 'src/app/components/shared-components.module';
import { GalleryHeaderComponent } from './components/gallery-header/gallery-header.component';
import { GalerieRoutingModule } from './galerie-routing.module';
import { GalleryLigthboxComponent } from './components/gallery-ligthbox/gallery-ligthbox.component';
import { HomeModule } from '../home/home.module';
import { GalleryComponent } from './components/gallery/gallery.component';

@NgModule({
  declarations: [ IndexComponent, GalleryHeaderComponent, GalleryLigthboxComponent, GalleryComponent ],
  imports: [
    CommonModule,
    SlickCarouselModule,
    SharedComponentsModule,
    GalerieRoutingModule,
    TranslateModule.forChild({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    HomeModule
  ],
  exports: [ GalleryComponent ]
})
export class GalerieModule { }
