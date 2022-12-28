import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddTimeSlotModalComponent } from './add-time-slot-modal.component';

describe('AddTimeSlotModalComponent', () => {
  let component: AddTimeSlotModalComponent;
  let fixture: ComponentFixture<AddTimeSlotModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddTimeSlotModalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddTimeSlotModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
