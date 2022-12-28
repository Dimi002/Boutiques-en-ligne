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
}
