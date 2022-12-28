import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalModule } from 'ngx-bootstrap/modal';
import { SectionTitleComponent } from './section-title/section-title.component';
import { FlagComponent } from './flag/flag.component';
import { LogoComponent } from './logo/logo.component';
import { LogoMiniComponent } from './logo-mini/logo-mini.component';
import { CrossWithImageComponent } from './cross-with-image/cross-with-image.component';
import { SectionSecondaryTitleComponent } from './section-secondary-title/section-secondary-title.component';
import { SectionAnnounceComponent } from './section-announce/section-announce.component';
import { SectionPageHeaderComponent } from './section-page-header/section-page-header.component';
import { GalleryImageViewerComponent } from './gallery-image-viewer/gallery-image-viewer.component';
import { SlickCarouselModule } from 'ngx-slick-carousel';
import { PageBannerBreadcrumbComponent } from './page-banner-breadcrumb/page-banner-breadcrumb.component';
import { RouterModule } from '@angular/router';
import { CustomImageComponent } from './custom-image/custom-image.component';
import { VideoLigthboxComponent } from './video-ligthbox/video-ligthbox.component';

@NgModule({
  declarations: [ SectionTitleComponent, FlagComponent, LogoComponent, LogoMiniComponent, CrossWithImageComponent, SectionSecondaryTitleComponent, SectionAnnounceComponent, SectionPageHeaderComponent, GalleryImageViewerComponent, PageBannerBreadcrumbComponent, CustomImageComponent, VideoLigthboxComponent ],
  imports: [
    CommonModule,
    RouterModule,
    ModalModule.forRoot(),
    SlickCarouselModule,
  ],
  exports: [ SectionTitleComponent, FlagComponent, LogoComponent, LogoMiniComponent, CrossWithImageComponent, SectionSecondaryTitleComponent, SectionAnnounceComponent, SectionPageHeaderComponent, PageBannerBreadcrumbComponent, CustomImageComponent, VideoLigthboxComponent ]
})
export class SharedComponentsModule { }
