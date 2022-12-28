import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DisplaySpecialitiesComponent } from './display-specialities.component';

describe('DisplaySpecialitiesComponent', () => {
  let component: DisplaySpecialitiesComponent;
  let fixture: ComponentFixture<DisplaySpecialitiesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DisplaySpecialitiesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DisplaySpecialitiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
