import { ViewChild, Component, Input, HostBinding } from '@angular/core';
import { SlickCarouselComponent } from 'ngx-slick-carousel';
import { Specialist } from 'src/app/generated';
import { ImageService } from 'src/app/services/image.service';

@Component({
  selector: 'app-doctor-image-slider',
  templateUrl: './doctor-image-slider.component.html',
  styleUrls: ['./doctor-image-slider.component.scss']
})
export class DoctorImageSliderComponent {
  @HostBinding('attr.class') cssClass = 'doctor-carousel';
  @ViewChild('slickComponent', { static: true }) slides!: SlickCarouselComponent;

  @Input() doctors: Specialist[] = [];
  
  constructor(
    public imageService: ImageService) { }

  public responsiveSlideOpts = [];

  public slideConfig = { slidesToShow: 1, slidesToScroll: 1, arrows: true, autoplay: false, centerMode: false, dots: false, responsive: this.responsiveSlideOpts };

  public addSlide(): void {
    this.doctors.push();
  }

  public removeSlide(): void {
    this.doctors.length = this.doctors.length - 1;
  }

  public slickInit(e: any): void {
  }

  public breakpoint(e: any): void {
  }

  public afterChange(e: any): void {
  }

  public beforeChange(e: any): void {
  }
}
