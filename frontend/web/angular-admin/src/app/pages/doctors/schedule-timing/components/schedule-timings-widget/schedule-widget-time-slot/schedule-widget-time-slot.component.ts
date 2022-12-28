import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-schedule-widget-time-slot',
  templateUrl: './schedule-widget-time-slot.component.html',
  styleUrls: ['./schedule-widget-time-slot.component.scss']
})
export class ScheduleWidgetTimeSlotComponent implements OnInit {
  @Input() interval!: { startAt: string, endAt: string, id?: number };
  @Output() remove: EventEmitter<{ startAt: string, endAt: string }> = new EventEmitter<{ startAt: string, endAt: string }>;

  constructor() { }

  ngOnInit(): void {
  }

  public onRemove(interval: { startAt: string, endAt: string, id?: number } | undefined): void {
    this.remove.emit(interval);
  }

}
