import { TestBed, async, ComponentFixture } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { NO_ERRORS_SCHEMA} from '@angular/core';

import { IndexComponent } from './index.component';
import { Image } from 'src/app/pages/gallery/components/index/index.component';
import { TranslateModule } from '@ngx-translate/core';
import { LanguageService } from 'src/app/services/language.service';
import { BrowserModule } from '@angular/platform-browser';

describe('Section About-US Component', () => {

  let component: IndexComponent;
  let fixture: ComponentFixture<IndexComponent>;
  let mockUserDetailsData: { description: string, img: string }[];
  let mockGalleryData: Image[];
  let languageService: LanguageService;

  beforeEach(async(() => {

      TestBed.configureTestingModule({
        imports: [
          BrowserModule,
          TranslateModule.forRoot(),],
        schemas: [ NO_ERRORS_SCHEMA ],
      });
      TestBed.overrideComponent(IndexComponent, {
        set: {
          providers: [
            { provide: LanguageService }
          ]
        }
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IndexComponent);
    component = fixture.debugElement.componentInstance;

    mockUserDetailsData = [
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
    mockGalleryData =  [
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
    ]

    component.aboutUsDetails = mockUserDetailsData
    component.galleryData = mockGalleryData
    languageService = TestBed.get(LanguageService);

    fixture.detectChanges();

  });


  it('should create the Section About-US component', () => {
    expect(component).toBeTruthy();
  });

  it('should verify expected data', () => {
    expect(component.aboutUsDetails).toBeDefined()
    expect(component.galleryData).toBeDefined()
    expect(component.aboutUsDetails).toEqual(mockUserDetailsData)
    expect(component.galleryData).toEqual(mockGalleryData)
  });

  it('When we call getTranslations()', () => {
    expect(component.aboutUsDetails).toBeDefined()
    expect(component.galleryData).toBeDefined()
    expect(component.aboutUsDetails).toEqual(mockUserDetailsData)
    expect(component.galleryData).toEqual(mockGalleryData)

    component.getTranslations()


  });

});
