import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditTimeSlotModalComponent } from './edit-time-slot-modal.component';

describe('EditTimeSlotModalComponent', () => {
  let component: EditTimeSlotModalComponent;
  let fixture: ComponentFixture<EditTimeSlotModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditTimeSlotModalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditTimeSlotModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
