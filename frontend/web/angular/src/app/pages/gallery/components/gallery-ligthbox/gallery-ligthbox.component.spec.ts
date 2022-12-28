import { TestBed, async, ComponentFixture } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AnimationEvent } from "@angular/animations";
import { NO_ERRORS_SCHEMA } from '@angular/core';

import { GalleryLigthboxComponent } from './gallery-ligthbox.component';
import { Image } from '../index/index.component';

describe('Component: Gallery Ligthbox', () => {

  let component: GalleryLigthboxComponent;
  let fixture: ComponentFixture<GalleryLigthboxComponent>
  let mockdata: Image[];

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule
      ],
      declarations: [
        GalleryLigthboxComponent
      ],
      schemas: [NO_ERRORS_SCHEMA]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GalleryLigthboxComponent);
    component = fixture.debugElement.componentInstance;
    mockdata = [
      {
        imageSrc: 'https://images.unsplash.com/photo-1609743522471-83c84ce23e32?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80',
        imageAlt: '1',
        imageTitle: ''
      },
      {
        imageSrc: 'https://images.unsplash.com/photo-1579684385127-1ef15d508118?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80',
        imageAlt: '2',
        imageTitle: ''
      },
      {
        imageSrc: 'https://images.unsplash.com/photo-1584467735867-4297ae2ebcee?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=705&q=80',
        imageAlt: '3',
        imageTitle: ''
      }
    ]

    component.galleryData = mockdata;
    fixture.detectChanges();

  });


  it('should create the Gallery Ligthbox component', () => {
    expect(component).toBeTruthy();
  });


  it('should verify expected data', () => {
    expect(component.galleryData).toBeDefined()
    expect(component.galleryData).toEqual(mockdata)
    expect(component.galleryData.length).toBe(3);
  });


  it('When we want to preview an image', () => {
    const selectedGalIndex: number = 2
    component.onPreviewImage(selectedGalIndex)

    expect(component.showMask).toBeTrue()
    expect(component.previewImage).toBeTrue()
    expect(component.currentIndex).toEqual(selectedGalIndex)
    expect(component.currentLightboxImage).toEqual(mockdata[selectedGalIndex])
  });


  it('When we want to close an image preview', () => {

    const previewImage: boolean = component.previewImage
    expect(component.previewImage).toBeTrue

    component.onClosePreview()

    expect(component.previewImage).toBeDefined
    expect(component.previewImage).toBeFalse
    expect(component.previewImage).toEqual(previewImage)
  });

  it('Go to the next image (current index = length -1)', () => {

    component.currentIndex = 2
    const currentIndex: number = component.currentIndex;
    component.next();

    if (currentIndex == component.galleryData.length - 1)
      expect(component.currentIndex).toEqual(0);
    else
      expect(component.currentIndex).toEqual(currentIndex + 1);
    expect(component.currentLightboxImage).toEqual(mockdata[component.currentIndex]);
  });

  it('Go to the next image (current index < length -1)', () => {

    component.currentIndex = 1
    const currentIndex: number = component.currentIndex;
    component.next();

    if (currentIndex == component.galleryData.length - 1)
      expect(component.currentIndex).toEqual(0);
    else
      expect(component.currentIndex).toEqual(currentIndex + 1);
    expect(component.currentLightboxImage).toEqual(mockdata[component.currentIndex]);
  });

  it('Go to the prev image (current index = 0)', () => {

    component.currentIndex = 0
    const currentIndex: number = component.currentIndex;

    component.prev();

    if (currentIndex == 0)
      expect(component.currentIndex).toEqual(component.galleryData.length - 1);
    else
      expect(component.currentIndex).toEqual(currentIndex - 1);

    expect(component.currentLightboxImage).toEqual(mockdata[component.currentIndex]);
  });

  it('Go to the prev image (current index > 0)', () => {

    component.currentIndex = 2
    const currentIndex: number = component.currentIndex;

    component.prev();

    if (currentIndex == 0)
      expect(component.currentIndex).toEqual(component.galleryData.length - 1);
    else
      expect(component.currentIndex).toEqual(currentIndex - 1);

    expect(component.currentLightboxImage).toEqual(mockdata[component.currentIndex]);
  });


  it('When an image animation event take end (toState void)', () => {

    const myCustomAnimationEvent: AnimationEvent = {
      fromState: 'void',
      toState: 'visible',
      totalTime: 0,
      phaseName: '',
      element: undefined,
      triggerName: '',
      disabled: false
    }

    expect(component.showMask).toBeTrue

    component.onAnimationEnd(myCustomAnimationEvent)

    if (myCustomAnimationEvent.toState === 'void')
      expect(component.showMask).toBeFalse
    else
      expect(component.showMask).toBeTrue
    expect(component.showMask).toBeFalse
  });

  it('When an image animation event take end (visible)', () => {

    const myCustomAnimationEvent: AnimationEvent = {
      fromState: 'visible',
      toState: 'void',
      totalTime: 0,
      phaseName: '',
      element: undefined,
      triggerName: '',
      disabled: false
    }

    expect(component.showMask).toBeTrue

    component.onAnimationEnd(myCustomAnimationEvent)

    if (myCustomAnimationEvent.toState === 'void')
      expect(component.showMask).toBeFalse
    else
      expect(component.showMask).toBeTrue
    expect(component.showMask).toBeFalse
  });


});
