import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TimingSlotDurationComponent } from './timing-slot-duration.component';

describe('TimingSlotDurationComponent', () => {
  let component: TimingSlotDurationComponent;
  let fixture: ComponentFixture<TimingSlotDurationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TimingSlotDurationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TimingSlotDurationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
