import { TestBed, async, ComponentFixture } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { GalleryHeaderComponent } from './gallery-header.component';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Image } from '../index/index.component';

describe('Component: Gallery Header', () => {

  let component: GalleryHeaderComponent;
  let fixture: ComponentFixture<GalleryHeaderComponent>
  let mockdata: { name: string, description: string, images: Image[], selected: boolean }[];


  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule
      ],
      declarations: [
        GalleryHeaderComponent
      ],
      schemas: [NO_ERRORS_SCHEMA]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GalleryHeaderComponent);
    component = fixture.debugElement.componentInstance;
    mockdata = [
      {
        name: 'All',
        description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        images: [
          {
            imageSrc: 'https://images.unsplash.com/photo-1609743522471-83c84ce23e32?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80',
            imageAlt: '1',
            imageTitle: ''
          },
        ],
        selected: true
      }
    ]
  });


  it('should create the Gallery Header component', () => {
    expect(component).toBeTruthy();
  });


  it('should verify expected data', () => {

    const name: string = "list images"
    const selected: boolean = false

    mockdata[0].name = name;
    mockdata[0].selected = selected

    component.galleryItems = mockdata;
    fixture.detectChanges();

    // the assertions

    expect(component.galleryItems).toBeDefined()
    expect(component.galleryItems.length).toBe(1);
    expect(component.galleryItems[0].name).toBe(name);
    expect(component.galleryItems[0].selected).toBe(selected);
  });


  it('When an Gallerie is selected', () => {

    const item: { name: string, description: string, images: Image[], selected: boolean } = mockdata[0]

    component.onSelectGalleryItem(item)

    expect(component.galleryItems).toBeDefined
    expect(component.galleryItems.length).toBe(6);
    component.galleryItems.forEach(gallery => {
      if (gallery === item) expect(gallery.selected).toBe(true);
      else expect(gallery.selected).toBe(false);
    })
    expect(component.gallerySelected).toBeDefined
  });


  it('should emit selected Image list', () => {
    // spy on event emitter
    spyOn(component.gallerySelected, 'emit');

    // trigger the click
    component.onSelectGalleryItem(mockdata[0])

    fixture.detectChanges();

    expect(component.gallerySelected.emit).toHaveBeenCalledWith(mockdata[0].images);
  });
});
