import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Contact } from 'src/app/generated';

@Component({
  selector: 'app-display-contacts',
  templateUrl: './display-contacts.component.html',
  styleUrls: ['./display-contacts.component.scss']
})
export class DisplayContactsComponent implements OnInit, OnDestroy {
  public dtOptions: DataTables.Settings = {};
  @Input() dtTrigger?: any;
  @Input() contacts: Contact[] = [];
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
