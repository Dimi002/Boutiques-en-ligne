import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScheduleWidgetTimeSlotComponent } from './schedule-widget-time-slot.component';

describe('ScheduleWidgetTimeSlotComponent', () => {
  let component: ScheduleWidgetTimeSlotComponent;
  let fixture: ComponentFixture<ScheduleWidgetTimeSlotComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ScheduleWidgetTimeSlotComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ScheduleWidgetTimeSlotComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
