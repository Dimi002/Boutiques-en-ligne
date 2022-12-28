import { Component, ViewChild } from '@angular/core';
import { SlickCarouselComponent } from 'ngx-slick-carousel';
import { TranslationsBaseDirective } from 'src/app/base-components/translations-base.component';
import { LanguageService } from 'src/app/services/language.service';

@Component({
  selector: 'app-section-availabe-features',
  templateUrl: './section-availabe-features.component.html',
  styleUrls: ['./section-availabe-features.component.scss']
})
export class SectionAvailabeFeaturesComponent extends TranslationsBaseDirective {
  @ViewChild('slickComponent', { static: true }) slides!: SlickCarouselComponent;

  public override translations: any = {
    sectionAvailable: {}
  }
  public features: { name: string, img: string }[] = [
    {
      name: 'Patient Ward',
      img: "assets/website/img/features/feature-01.jpg"
    },
    {
      name: 'Test Room',
      img: "assets/website/img/features/feature-02.jpg"
    },
    {
      name: 'ICU',
      img: "assets/website/img/features/feature-03.jpg"
    },
    {
      name: 'Laboratory',
      img: "assets/website/img/features/feature-04.jpg"
    },
    {
      name: 'Operation',
      img: "assets/website/img/features/feature-05.jpg"
    },
    {
      name: 'Medical',
      img: "assets/website/img/features/feature-06.jpg"
    }
  ];

  public data: { title: string, description: string } = { title: 'Availabe Features in Our Clinic', description: 'It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.' };

  public responsiveSlideOpts = [
    {
      breakpoint: 1024,
      settings: {
        slidesToShow: 3
      }
    }, {
      breakpoint: 700,
      settings: {
        slidesToShow: 3,
        autoplay: true,
        arrows: true
      }
  
    }, {
      breakpoint: 500,
      settings: {
        slidesToShow: 2,
        centerMode: true,
        autoplay: true,
        arrows: true
      }
    }, {
      breakpoint: 300,
      settings: {
        slidesToShow: 1,
        centerMode: true,
        autoplay: true,
        arrows: true
      }
    }
  ];

  public slideConfig = { slidesToShow: 4, slidesToScroll: 2, arrows: false, autoplay: true, centerMode: true, dots: true, infinite: true, responsive: this.responsiveSlideOpts };

  constructor( override languageService: LanguageService) {
    super(languageService);
  }

  public addSlide(): void {
    this.features.push();
  }

  public removeSlide(): void {
    this.features.length = this.features.length - 1;
  }

  public slickInit(e: any): void {
  }

  public breakpoint(e: any): void {
  }

  public afterChange(e: any): void {
  }

  public beforeChange(e: any): void {
  }

  override getTranslations(): void {
    this.languageService.get('home.pageTitle').subscribe((res: string) => {
      this.translations.pageTitle = res;
    });
    this.languageService.get('sectionAvailable.Titre').subscribe((res: string) => {
      this.data.title = res;
    });
    this.languageService.get('sectionAvailable.Description').subscribe((res: string) => {
      this.data.description = res;
    });
  }
}
