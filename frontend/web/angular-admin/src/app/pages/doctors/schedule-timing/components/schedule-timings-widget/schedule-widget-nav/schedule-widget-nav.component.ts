import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-schedule-widget-nav',
  templateUrl: './schedule-widget-nav.component.html',
  styleUrls: ['./schedule-widget-nav.component.scss']
})
export class ScheduleWidgetNavComponent implements OnInit {
  @Input() navItems: string[] = [];
  @Input() activeNavItem: string = '';

  @Output() select: EventEmitter<string> = new EventEmitter<string>();

  constructor() { }

  ngOnInit(): void {
  }

  public onSelect(selected: string): void {
    this.activeNavItem = selected;
    this.select.emit(selected);
  }

}
