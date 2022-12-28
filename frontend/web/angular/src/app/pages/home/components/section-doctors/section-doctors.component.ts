import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { SlickCarouselComponent } from 'ngx-slick-carousel';
import { TranslationsBaseDirective } from 'src/app/base-components/translations-base.component';
import { Specialist, SpecialistControllerService } from 'src/app/generated';
import { LanguageService } from 'src/app/services/language.service';

@Component({
  selector: 'app-section-doctors',
  templateUrl: './section-doctors.component.html',
  styleUrls: ['./section-doctors.component.scss']
})
export class SectionDoctorsComponent extends TranslationsBaseDirective implements AfterViewInit {
  @ViewChild('slickComponent', { static: true }) slides!: SlickCarouselComponent;

  public override translations: any = {
    sectionMedecin: {}
  }
  public doctors: Specialist[] = [];

  public data: { title: string, description: string } = { title: 'Our Doctors', description: '' };

  public responsiveSlideOpts = [
    {
      breakpoint: 2000,
      settings: {
        slidesToShow: 3
      }
    },
    {
      breakpoint: 1024,
      settings: {
        slidesToShow: 3
      }
    }, {
      breakpoint: 933,
      settings: {
        slidesToShow: 2,
        autoplay: true,
        arrows: true
      }

    }, {
      breakpoint: 619,
      settings: {
        slidesToShow: 1,
        centerMode: false,
        autoplay: true,
        arrows: true
      }
    }
  ];

  public slideConfig = { slidesToShow: 3, slidesToScroll: 1, arrows: true, autoplay: true, centerMode: false, dots: false, infinite: false, responsive: this.responsiveSlideOpts };
  public isLoading: boolean = false;

  constructor(
    public dataService: SpecialistControllerService, override languageService: LanguageService) {
    super(languageService);
  }

  ngAfterViewInit(): void {
    this.getData();
  }

  public getData(): void {
    this.isLoading = true;
    this.dataService.getAllActiveSpecialistUsingGET().toPromise().then(
      res => {
        this.doctors = res!;
      }
    ).finally(() => {
      this.isLoading = false;
    })
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

  override getTranslations(): void {
    this.languageService.get('home.pageTitle').subscribe((res: string) => {
      this.translations.pageTitle = res;
    });
    this.languageService.get('sectionMedecin.NosMedecins').subscribe((res: string) => {
      this.data.title = res;
    });
  }
}
