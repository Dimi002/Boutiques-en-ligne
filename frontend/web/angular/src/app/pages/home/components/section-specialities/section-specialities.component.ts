import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { SlickCarouselComponent } from 'ngx-slick-carousel';
import * as AOS from 'aos';
import { SpecialityControllerService, SpecialityMin } from 'src/app/generated';
import { ImageService } from 'src/app/services/image.service';
import { NavigationService } from 'src/app/services/navigation.service';
import { TranslationsBaseDirective } from 'src/app/base-components/translations-base.component';
import { LanguageService } from 'src/app/services/language.service';
@Component({
  selector: 'app-section-specialities',
  templateUrl: './section-specialities.component.html',
  styleUrls: ['./section-specialities.component.scss']
})
export class SectionSpecialitiesComponent extends TranslationsBaseDirective implements AfterViewInit {
  @ViewChild('slickComponent', { static: true }) slides!: SlickCarouselComponent;
  public override translations: any = {
    speciality: {}
  }
  public specialities: SpecialityMin[] = [];

  public data: { title: string, description: string } = { title: 'Clinic and Specialities', description: '' };

  public responsiveSlideOpts = [
    {
      breakpoint: 1200,
      settings: {
        slidesToShow: 3,
        arrows: true,
      }
    }, {
      breakpoint: 1024,
      settings: {
        slidesToShow: 2,
        arrows: true,
      }
    }, {
      breakpoint: 922,
      settings: {
        slidesToShow: 2,
        arrows: true,
      }
    }, {

      breakpoint: 600,
      settings: {
        slidesToShow: 1,
        autoplay: false,
        arrows: true
      }

    }, {
      breakpoint: 400,
      settings: {
        slidesToShow: 1,
        centerMode: false,
        autoplay: true,
        arrows: true
      }
    }
  ];

  public slideConfig = { slidesToShow: 4, slidesToScroll: 1, arrows: true, autoplay: true, centerMode: false, dots: true, infinite: false, responsive: this.responsiveSlideOpts };
  public isLoading: boolean = false;

  constructor(
    public dataService: SpecialityControllerService,
    public imageService: ImageService,
    public navService: NavigationService, override languageService: LanguageService) {
    super(languageService);
  }

  ngAfterViewInit(): void {
    AOS.init({ disable: 'mobile' });
    AOS.refresh();
    this.getData();
  }

  public getData(): void {
    this.isLoading = true;
    this.dataService.findAllSpecialitiesMinUsingGET().toPromise().then(
      res => {
        this.specialities = res!;
      }
    ).finally(() => {
      this.isLoading = false;
    })
  }

  public addSlide(): void {
    this.specialities.push();
  }

  public removeSlide(): void {
    this.specialities.length = this.specialities.length - 1;
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
    this.languageService.get('speciality.SavoirPlus').subscribe((res: string) => {
      this.translations.SavoirPlus = res;
    });
    this.languageService.get('speciality.TitreDeSection').subscribe((res: string) => {
      this.data.title = res;
    });
  }
}
