import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DoctorDefaultHeaderComponent } from './doctor-default-header.component';

describe('DoctorDefaultHeaderComponent', () => {
  let component: DoctorDefaultHeaderComponent;
  let fixture: ComponentFixture<DoctorDefaultHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DoctorDefaultHeaderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DoctorDefaultHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
