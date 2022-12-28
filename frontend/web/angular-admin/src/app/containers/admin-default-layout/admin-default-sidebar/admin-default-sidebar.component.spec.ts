import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDefaultSidebarComponent } from './admin-default-sidebar.component';

describe('AdminDefaultSidebarComponent', () => {
  let component: AdminDefaultSidebarComponent;
  let fixture: ComponentFixture<AdminDefaultSidebarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminDefaultSidebarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminDefaultSidebarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
