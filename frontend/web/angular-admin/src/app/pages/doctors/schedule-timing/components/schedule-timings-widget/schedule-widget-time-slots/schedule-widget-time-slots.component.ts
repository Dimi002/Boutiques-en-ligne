import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { BsModalService, BsModalRef } from "ngx-bootstrap/modal";
import { PlaningControllerService, PlaningDTO } from 'src/app/generated';
import { NotificationService } from 'src/app/services/notification.service';
import { AddTimeSlotModalComponent } from '../../add-time-slot-modal/add-time-slot-modal.component';
import { EditTimeSlotModalComponent } from '../../edit-time-slot-modal/edit-time-slot-modal.component';
@Component({
  selector: 'app-schedule-widget-time-slots',
  templateUrl: './schedule-widget-time-slots.component.html',
  styleUrls: ['./schedule-widget-time-slots.component.scss']
})
export class ScheduleWidgetTimeSlotsComponent implements OnInit {
  @Input() slot!: { day: string, slots: { startAt: string, endAt: string, id?: number }[] };

  @Output() deleteSlot: EventEmitter<{ startAt: string, endAt: string, id?: number }> = new EventEmitter<{ startAt: string, endAt: string, id?: number }>;
  constructor(
    public modalService: BsModalService,
    public notificationService: NotificationService,
    public planingService: PlaningControllerService,) { }

  ngOnInit(): void {
  }

  public remove(item: { startAt: string, endAt: string, id?: number }): void {
    var p: PlaningDTO = {
      id: item.id,
      startTime: item.startAt,
      endTime: item.endAt
    }
    this.planingService.deleteOneUsingDELETE(p).toPromise().then(res => {
      let index = this.slot.slots.findIndex(s => (s === item));
      if (index !== -1) {
        this.slot.slots.splice(index, 1);
      }
      this.notificationService.success('Planing has been removed successfully!!!')
    });
  }

  public openEditModal(slot: { day: string, slots: { startAt: string, endAt: string, id?: number }[] }): void {
    const initialState = { slots: slot.slots.slice().reverse(), day: this.slot.day };
    const modalRef: BsModalRef = this.modalService.show(EditTimeSlotModalComponent, {
      initialState, class: "edit-time-slot-modal modal-md modal-dialog-centered"
    });
    if (modalRef.onHidden) {
      modalRef.onHidden.subscribe(() => {
        const slots: { startAt: string, endAt: string, id?: number }[] = modalRef.content.slots;
        const hasSaved: boolean = modalRef.content.saved;
        if (hasSaved && slots && slots.length > 0) {
          this.slot.slots = slots;
        }
      });
    }
  }

  public openCreateModal(): void {
    const initialState = { day: this.slot.day };
    const modalRef: BsModalRef = this.modalService.show(AddTimeSlotModalComponent, {
      initialState, class: "add-time-slot-modal modal-md modal-dialog-centered",
    });
    if (modalRef.onHidden) {
      modalRef.onHidden.subscribe(() => {
        const slots: { startAt: string, endAt: string, id?: number }[] = modalRef.content.slots;
        const hasSaved: boolean = modalRef.content.saved;
        if (hasSaved && slots && slots.length > 0) {
          slots.forEach(slot => {
            if (!this.slot.slots.includes(slot)) {
              this.slot.slots.push(slot);
            }
          });
        }
      });
    }
  }

}
