import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDefaultHeaderComponent } from './admin-default-header.component';

describe('AdminDefaultHeaderComponent', () => {
  let component: AdminDefaultHeaderComponent;
  let fixture: ComponentFixture<AdminDefaultHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminDefaultHeaderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminDefaultHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
