import { TestBed, async, ComponentFixture } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { NO_ERRORS_SCHEMA} from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { BrowserModule, By } from '@angular/platform-browser';


import { SectionAboutDetailComponent } from './section-about-detail.component';


describe('Section About Details Component', () => {

  let component: SectionAboutDetailComponent;
  let fixture: ComponentFixture<SectionAboutDetailComponent>;
  let mockdata: { description: string, img: string };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        BrowserModule,
        RouterTestingModule,
        TranslateModule.forRoot(),
      ],
      declarations: [
        SectionAboutDetailComponent
      ],
      schemas: [ NO_ERRORS_SCHEMA ]
    }).compileComponents();

    fixture = TestBed.createComponent(SectionAboutDetailComponent);
    component = fixture.debugElement.componentInstance;

    mockdata = {
      description: 'My custom description',
      img: "assets/website/img/features/feature-01.jpg"
    }
    component.aboutUsDetail = mockdata;
    fixture.detectChanges();

  });


  it('should create the Section About-US component', () => {
    expect(component).toBeTruthy();
  });

  it('should verify expected data', () => {
    expect(component.aboutUsDetail).toBeDefined()
    expect(component.aboutUsDetail).toEqual(mockdata)
  });

  it('should have the expected description', () => {
    let query = fixture.debugElement.query(By.css('.about-description'));
    let p: HTMLElement  = query.nativeElement;

    expect(component.aboutUsDetail).toBeDefined()
    expect(component.aboutUsDetail).toEqual(mockdata)
    expect(p.innerText).toEqual(mockdata.description)
  });

  it('should have the expected image', () => {
    let query = fixture.debugElement.query(By.css('.cover'));
    let p: HTMLElement  = query.nativeElement;

    expect(component.aboutUsDetail).toBeDefined()
    expect(component.aboutUsDetail).toEqual(mockdata)
    expect(getComputedStyle(p).backgroundImage).toContain(mockdata.img)
  })

})
