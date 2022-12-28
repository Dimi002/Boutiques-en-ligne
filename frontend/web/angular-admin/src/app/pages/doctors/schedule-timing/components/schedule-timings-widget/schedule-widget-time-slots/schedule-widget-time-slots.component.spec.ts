import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScheduleWidgetTimeSlotsComponent } from './schedule-widget-time-slots.component';

describe('ScheduleWidgetTimeSlotsComponent', () => {
  let component: ScheduleWidgetTimeSlotsComponent;
  let fixture: ComponentFixture<ScheduleWidgetTimeSlotsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ScheduleWidgetTimeSlotsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ScheduleWidgetTimeSlotsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
