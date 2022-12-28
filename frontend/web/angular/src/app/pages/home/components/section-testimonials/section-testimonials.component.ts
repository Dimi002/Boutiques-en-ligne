import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { SlickCarouselComponent } from 'ngx-slick-carousel';
import * as AOS from 'aos';
import { TranslationsBaseDirective } from 'src/app/base-components/translations-base.component';
import { LanguageService } from 'src/app/services/language.service';

@Component({
  selector: 'app-section-testimonials',
  templateUrl: './section-testimonials.component.html',
  styleUrls: ['./section-testimonials.component.scss']
})
export class SectionTestimonialsComponent extends TranslationsBaseDirective implements AfterViewInit {
  @ViewChild('slickComponent', { static: true }) slides!: SlickCarouselComponent;
public override translations: any = {
  temoignage: {}
}
  public testimonials: { name: string, message: string }[] = [
    { name: 'Roney Ganou', message: 'Tétraplégique suite à un accident de football, je suis pris en charge depuis 2021 par La Clinique Médico-Chirugucale Le Berceau. Une infirmière vient chaque matin m’apporter une aide précieuse pour lancer ma journée (aide à la toilette, à l’habillage). C’est rassurant de savoir que CMCB met tout en œuvre pour m’assurer cette intervention quotidienne à domicile.' },
    { name: 'Yves Momo', message: 'After surgery, my wound healed badly and I had to be operated on again. Result: a hole 18 cm wide by 7 high and as deep… At the request of my surgeon, I was taken in charge by CMCB for negative pressure dressings. When I returned home, all the medical equipment was already installed to ensure a comfort of life similar to that of La Clinique.' },
    { name: 'Anne Dongmo', message: 'Je crois que c’est beaucoup par amour, pour mes enfants et mon mari et surtout avec l\'aide de tous les gentils médecins de la CMCB, que j’ai réussi à trouver les forces de me battre contre l’AVC.' },
    { name: 'Cyriaque Tagne', message: 'The nursing care I received was absolutely fantastic. I’m not planning on needing to, but if ever I had to be seen at a hospital again, I would love to come back to The Medical-Surgical Clinic Le Berceau.' }
  ];

  public data: { title: string, description: string } = { title: 'Our Patients Says', description: '' };

  public responsiveSlideOpts = [
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

  public slideConfig = { slidesToShow: 3, slidesToScroll: 1, arrows: true, autoplay: true, centerMode: false, dots: false, infinite: true, responsive: this.responsiveSlideOpts };

  constructor(override languageService: LanguageService) {
    super(languageService);
   }

  ngAfterViewInit(): void {
    AOS.init({ disable: 'mobile' });
    AOS.refresh();
  }

  public addSlide(): void {
    this.testimonials.push();
  }

  public removeSlide(): void {
    this.testimonials.length = this.testimonials.length - 1;
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
    this.languageService.get('temoignage.TitreSectionTemoignage').subscribe((res: string) => {
      this.data.title = res;
    });
  }
}
