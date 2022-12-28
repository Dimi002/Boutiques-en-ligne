import { TestBed, async, ComponentFixture } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { GalleryComponent } from './gallery.component';
import { IndexComponent } from './../index/index.component'
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { Image } from '../index/index.component';

describe('Component: Gallery', () => {

  let component: GalleryComponent;
  let fixture: ComponentFixture<GalleryComponent>

  let parentComponent: IndexComponent;
  let parentFixture: ComponentFixture<IndexComponent>

  let mockdata: Image[];

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule
      ],
      declarations: [
        GalleryComponent
      ],
      schemas: [NO_ERRORS_SCHEMA, CUSTOM_ELEMENTS_SCHEMA]
    }).compileComponents();
  }));

  beforeEach(() => {

    fixture = TestBed.createComponent(GalleryComponent);
    component = fixture.debugElement.componentInstance;

    parentFixture = TestBed.createComponent(IndexComponent);
    parentComponent = parentFixture.debugElement.componentInstance;


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


  it('should create the Gallery component', () => {
    expect(component).toBeTruthy();
  });


  it('should verify expected data', () => {
    expect(component.galleryData).toBeDefined()
    expect(component.showCount).toBeDefined()
    expect(component.showCount).toBeTrue
    expect(component.galleryData).toEqual(mockdata)
    expect(component.galleryData.length).toBe(3);
  });

  it('should emit selected Image to Gallery', () => {
    // spy on event emitter
    spyOn(component.gallerySelected, 'emit');

    // trigger the click
    component.onSelectGallery(mockdata)

    fixture.detectChanges();

    expect(component.gallerySelected.emit).toHaveBeenCalledWith(mockdata);
  });
});
