import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDefaultFooterComponent } from './admin-default-footer.component';

describe('AdminDefaultFooterComponent', () => {
  let component: AdminDefaultFooterComponent;
  let fixture: ComponentFixture<AdminDefaultFooterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminDefaultFooterComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminDefaultFooterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
