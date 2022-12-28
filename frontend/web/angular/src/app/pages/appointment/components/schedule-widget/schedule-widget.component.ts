import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { parseDayIntegerToString } from './parser';

@Component({
  selector: 'app-schedule-widget',
  templateUrl: './schedule-widget.component.html',
  styleUrls: ['./schedule-widget.component.scss']
})
export class ScheduleWidgetComponent implements OnInit {
  public weekSlots: { weekDay: string, date: Date, weekDaySlots: { time: string, selected: boolean, order: number, disabled: boolean }[], selected: boolean }[] = [];
  public disabledSlotRanges?: { day: number, startTime: string, endTime: string }[];
  @Input() slotsRange: { start: number, end: number } = { start: 8, end: 17 };
  @Output() weekDaysSlotsSelected: EventEmitter<{ time: string, selected: boolean, order: number, disabled: boolean }> = new EventEmitter<any>;

  public weekDays: string[] = ['SUNDAY', 'MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY'];

  constructor() { }

  public ngOnInit(): void {
    this.initWeekSlots();
  }

  public initWeekSlots(): void {
    const weekDaysDates: Date[] = this.weekDates();
    const weekDays = JSON.parse(JSON.stringify(this.weekDays));
    weekDays.forEach((weekDay: any, index: any) => {
      this.weekSlots.push({ weekDay: this.weekDays[weekDaysDates[index].getDay()], date: weekDaysDates[index], weekDaySlots: this.getDaysSlotAMAndPMFormat(), selected: false });
    })
  }

  public getDaysSlotAMAndPMFormat(): { time: string, selected: boolean, order: number, disabled: boolean }[] {
    const weekDaySlots: { time: string, selected: boolean, order: number, disabled: boolean }[] = []
    for (let i = (this.slotsRange.start ? this.slotsRange.start : 8), j = (i + 1); i <= (this.slotsRange.end ? this.slotsRange.end : 17); i++) {
      let slot0!: { time: string, selected: boolean, order: number, disabled: boolean };
      let slot30!: { time: string, selected: boolean, order: number, disabled: boolean };
      if (i <= 12) {
        if (i < 10) {
          slot0 = { time: '0' + i + ':00 AM', selected: false, order: i, disabled: false };
          slot30 = { time: '0' + i + ':30 AM', selected: false, order: j, disabled: false };
        } else {
          slot0 = { time: i + ':00 AM', selected: false, order: i, disabled: false };
          slot30 = { time: i + ':30 AM', selected: false, order: j, disabled: false };
        }
      } else {
        if ((i - 12) < 10) {
          slot0 = { time: '0' + (i - 12) + ':00 PM', selected: false, order: i, disabled: false };
          slot30 = { time: '0' + (i - 12) + ':30 PM', selected: false, order: j, disabled: false };
        } else {
          slot0 = { time: (i - 12) + ':00 PM', selected: false, order: i, disabled: false };
          slot30 = { time: (i - 12) + ':30 PM', selected: false, order: j, disabled: false };
        }
      }
      weekDaySlots.push(slot0);
      if (i < this.slotsRange.end)
        weekDaySlots.push(slot30);
    }
    return weekDaySlots;
  }

  public setDisabledSlotRanges(): void {
    this.reset();
    if (this.disabledSlotRanges && this.disabledSlotRanges.length > 0) {
      this.disabledSlotRanges.forEach(disableSlotRange => {
        const weekSlot = this.weekSlots.find(ws => (ws.weekDay.toLowerCase() === parseDayIntegerToString(disableSlotRange.day).toLowerCase()));
        if (weekSlot) {
          const startIndex = weekSlot.weekDaySlots.findIndex(wds => (wds.time.toLowerCase() === disableSlotRange.startTime.toLowerCase()));
          const endIndex = weekSlot.weekDaySlots.findIndex(wds => (wds.time.toLowerCase() === disableSlotRange.endTime.toLowerCase()));
          if (startIndex !== -1 && endIndex !== -1) {
            for (let i = startIndex; i <= endIndex; i++) {
              weekSlot.weekDaySlots[i].disabled = true;
              weekSlot.weekDaySlots[i].selected = false;
            }
          }
        }
      })
    }
  }

  public reset(): void {
    this.weekSlots.forEach(ws => {
      ws.selected = false;
      ws.weekDaySlots.forEach(wds => {
        wds.disabled = false;
        wds.selected = false
      })
    });
  }

  public onSelectWeekSlot(weekSlot: { weekDay: string, date: Date, weekDaySlots: { time: string, selected: boolean, order: number, disabled: boolean }[], selected: boolean } | undefined): void {
    if (weekSlot) {
      weekSlot.selected = !weekSlot.selected;
      this.weekSlots.forEach(slot => {
        if (slot.weekDay != weekSlot.weekDay) {
          slot.selected = false;
          slot.weekDaySlots.forEach(weekDay => {
            weekDay.selected = false;
          })
        }
      });
    }
  }

  public selectWeekSlotweekSlot(weekSlot: { weekDay: string, date: Date, weekDaySlots: { time: string, selected: boolean, order: number, disabled: boolean }[], selected: boolean }, daySlot: { time: string, selected: boolean, order: number, disabled: boolean }): void {
    weekSlot.selected = true;
    this.weekSlots.forEach(slot => {
      if (slot.weekDay != weekSlot.weekDay) {
        slot.selected = false;
        slot.weekDaySlots.forEach(weekDay => {
          weekDay.selected = false;
        })
      } else {
        weekSlot.weekDaySlots.forEach(weekDaySlot => {
          if (weekDaySlot != daySlot) {
            weekDaySlot.selected = false;
          }
        });
      }
    });
  }

  public onSelectDaySlot(weekSlot: { weekDay: string, date: Date, weekDaySlots: { time: string, selected: boolean, order: number, disabled: boolean }[], selected: boolean }, daySlot: { time: string, selected: boolean, order: number, disabled: boolean }): void {
    if (!daySlot.disabled) {
      daySlot.selected = !daySlot.selected;
      this.selectWeekSlotweekSlot(weekSlot, daySlot);
      this.weekDaysSlotsSelected.emit(this.getFirstsConsecutivesDaySlots(weekSlot)[0]);
    }
  }

  public getAllSelectedSlots(): void {
    const weekSlot: { weekDay: string, date: Date, weekDaySlots: { time: string, selected: boolean, order: number, disabled: boolean }[], selected: boolean } = this.weekSlots.find(slot => {
      return slot.selected;
    })!;

  }

  public getFirstsConsecutivesDaySlots(weekSlot: { weekDay: string, date: Date, weekDaySlots: { time: string, selected: boolean, order: number, disabled: boolean }[], selected: boolean }): { time: string, selected: boolean, order: number, disabled: boolean }[] {
    const firstSelectedDaySlotIndex: number = weekSlot.weekDaySlots.findIndex(daySlot => (daySlot.selected))!;
    const firstConsecutivesDaysSlotsResult: any[] = [];
    if (weekSlot.weekDaySlots[firstSelectedDaySlotIndex])
      firstConsecutivesDaysSlotsResult.push(weekSlot.weekDaySlots[firstSelectedDaySlotIndex]);
    const firstSelectedDaySlot = firstConsecutivesDaysSlotsResult[0];
    firstSelectedDaySlot.date = weekSlot.date;
    for (let i = firstSelectedDaySlotIndex + 1; i < weekSlot.weekDaySlots.length; i++) {
      if (weekSlot.weekDaySlots[i] && weekSlot.weekDaySlots[i].selected) {
        const selectedDaySlot: any = weekSlot.weekDaySlots[i + 1];
        selectedDaySlot.date = weekSlot.date;
        firstConsecutivesDaysSlotsResult.push(selectedDaySlot);
      } else {
        break;
      }
    }
    return firstConsecutivesDaysSlotsResult;
  }

  public weekDates(): Date[] {
    const current: Date = new Date();
    const week: Date[] = new Array<Date>();
    for (var i = 0; i < 7; i++) {
      week.push(
        new Date(current)
      );
      current.setDate(current.getDate() + 1);
    }
    return week;
  }

}
