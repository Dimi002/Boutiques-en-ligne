import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScheduleWidgetNavComponent } from './schedule-widget-nav.component';

describe('ScheduleWidgetNavComponent', () => {
  let component: ScheduleWidgetNavComponent;
  let fixture: ComponentFixture<ScheduleWidgetNavComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ScheduleWidgetNavComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ScheduleWidgetNavComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
