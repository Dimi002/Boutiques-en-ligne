import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Appointment } from 'src/app/generated';
import { ImageService } from 'src/app/services/image.service';

@Component({
  selector: 'app-display-appointments',
  templateUrl: './display-appointments.component.html',
  styleUrls: ['./display-appointments.component.scss']
})
export class DisplayAppointmentsComponent implements OnInit, OnDestroy {
  public dtOptions: DataTables.Settings = {};
  @Input() dtTrigger?: any;
  @Input() appointments: Appointment[] = [];
  @Input() isLoading: boolean = false;

  constructor(
    public imageService: ImageService) { }

  ngOnInit(): void {
    this.getData();
  }

  public getData(): void {
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 10
    };
  }

  public ngOnDestroy(): void {
    this.dtTrigger.unsubscribe();
  }

  public getHour(data: string | undefined): string {
    if (data)
      return data.substring(data.indexOf('T') + 1, data.indexOf('T') + 6);
    return '';
  }
}
