import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-gallery-image-viewer',
  templateUrl: './gallery-image-viewer.component.html',
  styleUrls: ['./gallery-image-viewer.component.scss']
})
export class GalleryImageViewerComponent {
  @Input() images: string[] = [];
  @Input() currentImage: string = '';

  constructor() { }

  public responsiveSlideOpts = [];
  public slideConfig = { slidesToShow: 1, slidesToScroll: 1, arrows: true, autoplay: false, centerMode: false, dots: false, responsive: this.responsiveSlideOpts };

  public slickInit(e: any): void {
  }

  public breakpoint(e: any): void {
  }

  public afterChange(e: any): void {
  }

  public beforeChange(e: any): void {
  }

}
