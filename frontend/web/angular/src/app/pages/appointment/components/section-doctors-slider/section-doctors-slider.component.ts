import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { SlickCarouselComponent } from 'ngx-slick-carousel';
import { Specialist } from 'src/app/generated';

@Component({
  selector: 'app-section-doctors-slider',
  templateUrl: './section-doctors-slider.component.html',
  styleUrls: ['./section-doctors-slider.component.scss']
})
export class SectionDoctorsSliderComponent {
  @ViewChild('slickComponent', { static: true }) slides!: SlickCarouselComponent;
  @Output() doctorSelected: EventEmitter<any> = new EventEmitter<Specialist>();

  @Input() doctors: any[] = [];

  public responsiveSlideOpts = [
    {
      breakpoint: 1024,
      settings: {
        slidesToShow: 3
      }
    }, {
      breakpoint: 933,
      settings: {
        slidesToShow: 2
      }
  
    }, {
      breakpoint: 619,
      settings: {
        slidesToShow: 1
      }
    }
  ];

  public slideConfig = { slidesToShow: 4, slidesToScroll: 1, arrows: true, autoplay: false, centerMode: false, dots: false, infinite: false, responsive: this.responsiveSlideOpts };

  constructor() { }

  public onSelect(data: any): void {
    this.doctors.forEach(doctor => {
      if (doctor !== data) {
        doctor.selected = false;
      }
    });
    this.doctorSelected.emit(data);
  }
  
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
