import { getConfiguration } from 'src/app/app.module';
import { TestBed, ComponentFixture, async } from '@angular/core/testing';

import { SectionSpecialitiesComponent } from './section-specialities.component';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ModalModule } from 'ngx-bootstrap/modal';
import { SlickCarouselModule } from 'ngx-slick-carousel';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { SpecialityMin, SpecialityControllerService, ApiModule } from 'src/app/generated';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { LanguageService } from 'src/app/services/language.service';
import { TranslateModule } from '@ngx-translate/core';

describe('Component: Section Speciality', () => {

  let component: SectionSpecialitiesComponent;
  let fixture: ComponentFixture<SectionSpecialitiesComponent>

  let specialities: SpecialityMin[];
  let data: { title: string, description: string };
  let responsiveSlideOpts: any
  let slideConfig: any;
  let isLoading: boolean;

  let languageService: LanguageService;
  let specialityControllerService: SpecialityControllerService;


  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SectionSpecialitiesComponent ],
      imports: [
        CommonModule,
        RouterModule,
        ModalModule.forRoot(),
        SlickCarouselModule,
        BrowserModule,
        HttpClientModule,
        TranslateModule.forRoot(),
        ApiModule.forRoot(getConfiguration),
      ],
      providers: [
        LanguageService,
        SpecialityControllerService,
    ],
      schemas: [ NO_ERRORS_SCHEMA ]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SectionSpecialitiesComponent);
    component = fixture.debugElement.componentInstance;

    specialityControllerService = TestBed.inject(SpecialityControllerService);
    languageService = TestBed.inject(LanguageService);

    // initialisation des attributs du composant
    specialities = []
    data =  { title: 'Clinic and Specialities', description: '' };
    responsiveSlideOpts = [
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
    ]
    slideConfig =  { slidesToShow: 4, slidesToScroll: 1, arrows: true, autoplay: true, centerMode: false, dots: true, infinite: false, responsive: responsiveSlideOpts };
    isLoading = false;

    // initialisatoin du composant
    component.specialities = specialities
    component.slideConfig = slideConfig
    component.isLoading = isLoading
    component.data = data

    fixture.detectChanges();
  });


  it('should create the Section Speciality', () => {
    expect(component).toBeTruthy();
  });


  it('should verify expected data', () => {
    expect(component.specialities).toBeDefined()
    expect(component.slideConfig).toBeDefined()
    expect(component.isLoading).toBeDefined()
    expect(component.data).toBeDefined()

    expect(component.specialities).toBe(specialities)
    expect(component.slideConfig).toBe(slideConfig)
    expect(component.data).toBe(data)
  });

  // it('When we want to add a slide: addSlide()', async () => {
  //   component.getData()
  // });



});
