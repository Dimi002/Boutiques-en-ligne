import { Component } from '@angular/core';
import { TranslationsBaseDirective } from 'src/app/base-components/translations-base.component';
import { Image } from 'src/app/pages/gallery/components/index/index.component';
import { LanguageService } from 'src/app/services/language.service';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.scss']
})
export class IndexComponent extends TranslationsBaseDirective {

  public override translations: any = {
    aboutUs: {}
  }
  public aboutUsDetails: { description: string, img: string }[] = [
    {
      description: '',
      img: "assets/website/img/features/feature-01.jpg"
    },
    {
      description: '',
      img: "assets/website/img/features/feature-02.jpg"
    },
    {
      description: '',
      img: "assets/website/img/features/feature-05.jpg"
    },
    {
      description: '',
      img: "assets/website/img/features/feature-01.jpg"
    }
  ];

  galleryData: Image[] = [
    {
      imageSrc: 'https://images.unsplash.com/photo-1609743522471-83c84ce23e32?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80',
      imageAlt: '1',
      imageTitle: 'Image 1'
    },
    {
      imageSrc: 'https://images.unsplash.com/photo-1579684385127-1ef15d508118?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80',
      imageAlt: '2',
      imageTitle: 'Image 2'
    },
    {
      imageSrc: 'https://images.unsplash.com/photo-1584467735867-4297ae2ebcee?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=705&q=80',
      imageAlt: '3',
      imageTitle: 'Image 3'
    },
    {
      imageSrc: 'https://images.unsplash.com/photo-1642649149963-0ef6779df6c6?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80',
      imageAlt: '4',
      imageTitle: 'Image 4'
    },
    {
      imageSrc: 'https://images.unsplash.com/photo-1642618215095-3523a9a36893?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80',
      imageAlt: '5',
      imageTitle: 'Image 5'
    },
    {
      imageSrc: 'https://images.unsplash.com/photo-1514415679929-1fd5193f14f7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80',
      imageAlt: '6',
      imageTitle: 'Image 6'
    },
    {
      imageSrc: 'https://images.unsplash.com/photo-1490730141103-6cac27aaab94?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80',
      imageAlt: '7',
      imageTitle: 'Image 7'
    },
    {
      imageSrc: 'https://images.unsplash.com/photo-1501854140801-50d01698950b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=975&q=80',
      imageAlt: '8',
      imageTitle: 'Image 8'
    },
    {
      imageSrc: 'https://images.unsplash.com/photo-1441974231531-c6227db76b6e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=871&q=80',
      imageAlt: '9',
      imageTitle: 'Image 9'
    },
    {
      imageSrc: 'https://images.unsplash.com/photo-1523712999610-f77fbcfc3843?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80',
      imageAlt: '10',
      imageTitle: 'Image 10'
    },
    {
      imageSrc: 'https://images.unsplash.com/photo-1470770903676-69b98201ea1c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80',
      imageAlt: '11',
      imageTitle: 'Image 11'
    },
    {
      imageSrc: 'https://images.unsplash.com/photo-1465189684280-6a8fa9b19a7a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80',
      imageAlt: '12',
      imageTitle: 'Image 12'
    },
    {
      imageSrc: 'https://images.unsplash.com/photo-1578496781985-452d4a934d50?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80',
      imageAlt: '13',
      imageTitle: 'Image 13'
    },
    {
      imageSrc: 'https://images.unsplash.com/photo-1631815587646-b85a1bb027e1?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=686&q=80',
      imageAlt: '7',
      imageTitle: 'Image 14'
    },
    {
      imageSrc: 'https://images.unsplash.com/photo-1629909613654-28e377c37b09?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1168&q=80',
      imageAlt: '8',
      imageTitle: 'Image 15'
    },
    {
      imageSrc: 'https://images.unsplash.com/photo-1605369473971-c5e417ac3220?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80',
      imageAlt: '9',
      imageTitle: 'Image 16'
    },
  ]

  constructor(override languageService: LanguageService) {
    super(languageService);
  }

  override getTranslations(): void {
    this.languageService.get('home.pageTitle').subscribe((res: string) => {
      this.translations.pageTitle = res;
    });
    this.languageService.get('aboutUs.Description1').subscribe((res: string) => {
      this.aboutUsDetails[0].description = res;
    });
    this.languageService.get('aboutUs.Description2').subscribe((res: string) => {
      this.aboutUsDetails[1].description = res;
    });
    this.languageService.get('aboutUs.Description3').subscribe((res: string) => {
      this.aboutUsDetails[2].description = res;
    });
    this.languageService.get('aboutUs.Description4').subscribe((res: string) => {
      this.aboutUsDetails[3].description = res;
    });
  }
}
