import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Appointment } from 'src/app/generated';

@Component({
  selector: 'app-display-patients',
  templateUrl: './display-patients.component.html',
  styleUrls: ['./display-patients.component.scss']
})
export class DisplayPatientsComponent implements OnInit, OnDestroy {
  public dtOptions: DataTables.Settings = {};
  @Input() dtTrigger?: any;
  @Input() patients: Appointment[] = [];
  @Input() isLoading: boolean = false;

  constructor() { }

  ngOnInit(): void {
    this.getData();
  }

  public getData(): void {
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 4
    };
  }

  public detail(item: any): void {
  }

  public delete(item: any): void {
  }

  public cancel(item: any): void {
  }

  public ngOnDestroy(): void {
    this.dtTrigger.unsubscribe();
  }
}
