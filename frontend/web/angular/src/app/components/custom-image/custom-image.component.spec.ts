import { TestBed, ComponentFixture, async } from '@angular/core/testing';

import { CustomImageComponent } from './custom-image.component';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ModalModule } from 'ngx-bootstrap/modal';
import { SlickCarouselModule } from 'ngx-slick-carousel';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ApiModule, UploadControllerService } from 'src/app/generated';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { getConfiguration } from 'src/app/app.module';

describe('Component: Custom Image', () => {

  let component: CustomImageComponent;
  let fixture: ComponentFixture<CustomImageComponent>

  let src: string | undefined;
  let defaultSrc: string;
  let alt: string | undefined;
  let imgClass: string | undefined;
  let baseImagePAth: string;

  let uploadControllerService: any;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomImageComponent ],
      imports: [
        CommonModule,
        RouterModule,
        ModalModule.forRoot(),
        SlickCarouselModule,
        BrowserModule,
        ApiModule.forRoot(getConfiguration)
      ],
      providers: [
        UploadControllerService,
    ],
      schemas: [ NO_ERRORS_SCHEMA ]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomImageComponent);
    component = fixture.debugElement.componentInstance;

    uploadControllerService = TestBed.inject(UploadControllerService);

    src = "path/to/an/image"
    defaultSrc = "my/custom/image/path"
    alt = "image name"
    imgClass = ".image-custom"
    baseImagePAth = "/file/download?fileKey="

    // initialisatoin du composant
    component.alt = alt
    component.imgClass = imgClass
    component.defaultSrc = defaultSrc

    fixture.detectChanges();
  });


  it('should create the Custom Image component', () => {
    expect(component).toBeTruthy();
  });


  it('should verify expected data', () => {
    // expect(component.alt).toBeDefined()
    expect(component.imgClass).toBeDefined()
    expect(component.alt).toBeDefined()

    expect(component.alt).toBe(alt)
    expect(component.imgClass).toBe(imgClass)
    expect(component.alt).toBe(alt)
  });

  it('When we want to set the src field (with an correct image link)', async () => {
    component.src = baseImagePAth + 'Storage/Images/1665402814577-chirurgie.jpeg';
  });


  it('When we want to store an image)', async () => {
    component.storeImage(baseImagePAth + 'Storage/Images/1665402814577-chirurgie.jpeg')
  });

});
