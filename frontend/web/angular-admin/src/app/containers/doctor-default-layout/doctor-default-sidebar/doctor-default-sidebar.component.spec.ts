import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DoctorDefaultSidebarComponent } from './doctor-default-sidebar.component';

describe('DoctorDefaultSidebarComponent', () => {
  let component: DoctorDefaultSidebarComponent;
  let fixture: ComponentFixture<DoctorDefaultSidebarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DoctorDefaultSidebarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DoctorDefaultSidebarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
