import { GalleryComponent } from './../gallery/gallery.component';
import { TestBed, async, ComponentFixture } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { IndexComponent } from './index.component';
import { NO_ERRORS_SCHEMA } from '@angular/core';

describe('Component: Gallery Index', () => {

  let component: IndexComponent;
  let fixture: ComponentFixture<IndexComponent>

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule
      ],
      declarations: [
        IndexComponent, GalleryComponent
      ],
      schemas: [NO_ERRORS_SCHEMA]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IndexComponent);
    component = fixture.componentInstance;
  });

  it('should create the index component', () => {
    expect(component).toBeTruthy();
  });


  it('should verify expected data', () => {


    const mockdata = [
      {
        imageSrc: 'https://images.unsplash.com/photo-1609743522471-83c84ce23e32?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80',
        imageAlt: '1',
        imageTitle: ''
      },
      {
        imageSrc: 'https://images.unsplash.com/photo-1579684385127-1ef15d508118?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80',
        imageAlt: 'IMG ALT 2',
        imageTitle: 'Image 2 Title'
      }
    ]

    const mockdataTemoin = [
      {
        imageSrc: 'https://images.unsplash.com/photo-1609743522471-83c84ce23e32?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80',
        imageAlt: 'IMG ALT 1',
        imageTitle: 'Image 1 Title'
      },
      {
        imageSrc: 'https://images.unsplash.com/photo-1579684385127-1ef15d508118?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80',
        imageAlt: 'IMG ALT 2',
        imageTitle: 'Image 2 Title'
      }
    ]

    //@ts-ignore
    mockdata[0].imageAlt = 'IMG ALT 1';
    //@ts-ignore
    mockdata[0].imageTitle = 'Image 1 Title';

    component.data = mockdata;
    fixture.detectChanges();

    // the assertions

    expect(component.data).toBeDefined()
    expect(component.data).toEqual(mockdataTemoin)
    expect(component.data.length).toBe(2);
    expect(component.data[0].imageAlt).toBe('IMG ALT 1');
    expect(component.data[0].imageTitle).toBe('Image 1 Title');
  });

});
