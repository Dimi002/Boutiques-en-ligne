import { Component, OnInit } from '@angular/core';
import { PlaningDTO, User } from 'src/app/generated';
import { PlaningControllerService } from 'src/app/generated/api/planingController.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { parseDayStringToInteger } from 'src/app/utils/parser';

@Component({
  selector: 'app-schedule-widget',
  templateUrl: './schedule-widget.component.html',
  styleUrls: ['./schedule-widget.component.scss']
})
export class ScheduleWidgetComponent implements OnInit {
  public navItems: string[] = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
  public weekDaysWithSlots: { day: string, slots: { startAt: string, endAt: string, id?: number }[] } = {
    day: this.navItems[0],
    slots: []
  };
  public activeNavItem: string = this.navItems[0];
  public activeTab: number = 1;
  public user: User = this.authService.getUser();

  constructor(
    public planingService: PlaningControllerService,
    public authService: AuthenticationService,) { }

  ngOnInit(): void {
    this._initialization();
  }

  _initialization() {
    this.getSlots();
  }

  public onSelectDay(itemSelected: string): void {
    this.activeNavItem = itemSelected;
    this.getSlots(this.activeNavItem);
  }

  getSlots(day: string = this.navItems[0]) {
    if (this.user.specialist) {
      this.planingService.recordsUsingGET1(this.user.specialist?.specialistId!, parseDayStringToInteger(day)).toPromise().then(e => {
        const data: { startAt: string, endAt: string, id?: number }[] = [];
        e?.forEach((p) => {
          data.push({
            startAt: p.startTime ?? '',
            endAt: p.endTime ?? '',
            id: p.id
          })
        });
        this.weekDaysWithSlots.day = day;
        this.weekDaysWithSlots.slots = data;
      });
    }
  }


}
