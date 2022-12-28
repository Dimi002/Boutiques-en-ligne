import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DoctorDefaultFooterComponent } from './doctor-default-footer.component';

describe('DoctorDefaultFooterComponent', () => {
  let component: DoctorDefaultFooterComponent;
  let fixture: ComponentFixture<DoctorDefaultFooterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DoctorDefaultFooterComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DoctorDefaultFooterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
