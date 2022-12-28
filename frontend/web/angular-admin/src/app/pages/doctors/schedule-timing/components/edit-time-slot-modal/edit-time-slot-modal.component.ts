import { Component, OnInit } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { ModalBaseComponent } from 'src/app/components/base/modal-base.component';
import { Setting, SettingControllerService, User } from 'src/app/generated';
import { PlaningControllerService } from 'src/app/generated/api/planingController.service';
import { PlaningDTO } from 'src/app/generated/model/planingDTO';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { NotificationService } from 'src/app/services/notification.service';
import { PLANINGS_TIMES } from 'src/app/utils/constants';
import { parseDayStringToInteger } from 'src/app/utils/parser';
import { planingsTimesMaker } from 'src/app/utils/planings-times';

@Component({
  selector: 'app-edit-time-slot-modal',
  templateUrl: './edit-time-slot-modal.component.html',
  styleUrls: ['./edit-time-slot-modal.component.scss']
})
export class EditTimeSlotModalComponent implements OnInit {

  public slots!: { startAt: string, endAt: string, id?: number }[];
  public saved: boolean = false;
  public user: User = this.authService.getUser();
  public day!: string;
  public times!: any;

  constructor(
    public modalService: BsModalRef,
    public planingService: PlaningControllerService,
    public authService: AuthenticationService,
    public notificationService: NotificationService,
    public settingService: SettingControllerService) {
  }

  public close(): boolean {

    this.modalService.hide();
    return false;
  }

  ngOnInit(): void {
    this._initialisation();
  }

  _initialisation() {
    this.settingService.recordsUsingGET3().toPromise().then((res?: Setting) => {
      this.times = planingsTimesMaker(res?.planingStartAt ?? 8, res?.planingEndAt ?? 17);
    }).catch(error => {
      if (error && error?.errorText == 'The setting is null')
        this.notificationService.success('The setting is null')
    })
  }

  public addSlot(): void {

    if (!this.verify()) {
      alert('The slot you add already exist');
      return;
    }
    const slot: { startAt: string, endAt: string, id: number } = {
      startAt: '-',
      endAt: '-',
      id: 0
    };
    this.slots.push(slot);
  }

  public verify(): boolean {
    if (this.slots && this.slots.length > 0) {
      const lastSlot = this.slots[this.slots.length - 1];
      if (lastSlot.startAt === '-' || lastSlot.endAt === '-') {
        return false;
      }
      for (let i = 0; i < this.slots.length - 1; i++) {
        const slot = this.slots[i];
        if (slot.startAt === lastSlot.startAt && slot.endAt === lastSlot.endAt) {
          return false;
        }
      }
      return true;
    }
    return true;
  }

  public removeSlot(slot: { startAt: string, endAt: string, id?: number }): void {
    const index: number = this.slots.findIndex(s => (s === slot));
    if (index !== -1) {
      this.slots.splice(index, 1);
      return;
    }
    return;
  }

  public saveChanges(): void {
    this.slots.forEach((slot, i) => {
      if (slot.startAt === '-' || slot.endAt === '-') {
        this.slots.splice(i, 1);
      }
    });
    let slots: { startAt: string, endAt: string, id?: number }[] = [];
    slots.push(...this.slots);
    slots = slots.filter((value, index, self) =>
      index === self.findIndex((t) => (
        t.startAt === value.startAt && t.endAt === value.endAt
      ))
    );
    let listPlanings: PlaningDTO[] = [];
    slots.forEach(e => {
      listPlanings.push({
        planDay: parseDayStringToInteger(this.day),
        startTime: e.startAt,
        endTime: e.endAt,
        specialist: this.user.specialist,
      })
    })

    this.planingService.updateUsingPOST(listPlanings).toPromise().then(e => {
      if (e) {
        this.saved = true;
        this.planingService.recordsUsingGET1(this.user.specialist?.specialistId ?? 0, parseDayStringToInteger(this.day)).toPromise().then(res => {
          this.slots = [];
          res?.forEach(p => {
            this.slots.push({
              startAt: p.startTime ?? '',
              endAt: p.endTime ?? '',
              id: p.id ?? 0
            })
          })
        })
      } else {
        this.notificationService.danger('An error has occurred!!!');
      }
    }).finally(() => {
      this.close();
    })


    this.saved = true;
    this.close();
  }

  checkValidSelect() {
    throw new Error('Method not implemented.');
  }
}
