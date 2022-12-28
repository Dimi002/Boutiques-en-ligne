import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScheduleMainCardComponent } from './schedule-main-card.component';

describe('ScheduleMainCardComponent', () => {
  let component: ScheduleMainCardComponent;
  let fixture: ComponentFixture<ScheduleMainCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ScheduleMainCardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ScheduleMainCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
