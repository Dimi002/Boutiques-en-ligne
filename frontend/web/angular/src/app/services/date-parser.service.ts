import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class DateParserService {
  constructor() { }

  public parseToLocalFr(date: Date): string {
    const options: any = { weekday: 'long', month: 'long', day: 'numeric', year: 'numeric', hour: 'numeric', minute: 'numeric' };
    return date.toLocaleDateString('fr-FR', options);
  }

  public formatYYYYMMDDDate(date: Date): string {
    var d = new Date(date),
      month = '' + (d.getMonth() + 1),
      day = '' + d.getDate(),
      year = d.getFullYear();

    if (month.length < 2)
      month = '0' + month;
    if (day.length < 2)
      day = '0' + day;

    return [year, month, day].join('-');
  }

  public setMinutesTo00or30(appointmentDate: string, hours: any, minutes: number, suffix?: string): string {
    let hourToSet = hours;
    if (suffix && suffix.trim().toLocaleLowerCase() === 'pm') {
      hourToSet = (hourToSet + 12);
    }
    let appointmentHour: string = appointmentDate + ''
    hourToSet = hourToSet < 10 ? '0' + hourToSet : hourToSet;
    appointmentHour = appointmentHour + 'T' + hourToSet;
    if (minutes >= 0 && minutes <= 15) {
      appointmentHour = appointmentHour + ':00';
    } else if (minutes > 15 && minutes < 40) {
      appointmentHour = appointmentHour + ':30';
    } else if (minutes >= 40) {
      const newHourToSet = hours++ < 10 ? '0' + (hours++) : (hours++);
      appointmentHour = appointmentDate + 'T' + newHourToSet + ':00';
    }
    return appointmentHour;
  }

  public formatDDMMYYYYDate(date: Date): string {
    var d = new Date(date),
      month = '' + (d.getMonth() + 1),
      day = '' + d.getDate(),
      year = d.getFullYear();

    if (month.length < 2)
      month = '0' + month;
    if (day.length < 2)
      day = '0' + day;

    return [day, month, year].join('/');
  }

  public compareDates(d1: string | undefined, d2: string | undefined): string {
    let status: string = "";
    if (d1 && d2) {
      const date1 = this.convertToDate(d1);
      const date2 = this.convertToDate(d2);
      if (date1 > date2) {
        status = "greater";
      } else if (date1 < date2) {
        status = "less";
      } else {
        status = "equal";
      }
    }
    return status;
  }

  public convertToDate(date: any): Date {
    const [day, month, year] = date.split("/");
    return new Date(year, month - 1, day);
  }

  public parseAppointmentHour(inputHour: string | undefined): string {
    let outputHour: string = "";
    let prefix1: number = 0;
    let prefix2: number = 0;

    if (inputHour) {
      prefix1 = Number(inputHour.slice(0, 1)[0]);
      prefix2 = Number(inputHour.slice(0, 2)[1]);
      if ((prefix1 == 1 && prefix2 <= 2) || (prefix1 == 0 && prefix2 >= 1 && prefix2 <= 9)) {
        // l'espace mis avant PM ou AM est très important: à ne pas rétirer
        outputHour = inputHour + " AM";
      } else if ((prefix1 == 1 && prefix2 >= 3 && prefix2 <= 9) || (prefix1 == 2 && prefix2 >= 0 && prefix2 <= 4) || (prefix1 == 0 && prefix2 == 0)) {
        outputHour = inputHour + " PM";
      }
    }
    return outputHour;
  }
}
