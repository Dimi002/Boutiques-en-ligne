import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { ImageService } from 'src/app/services/image.service';

@Component({
  selector: 'app-display-doctors',
  templateUrl: './display-doctors.component.html',
  styleUrls: ['./display-doctors.component.scss']
})
export class DisplayDoctorsComponent implements OnInit, OnDestroy {
  public dtOptions: DataTables.Settings = {};
  @Input() dtTrigger?: any;
  @Input() items: any[] = [];
  @Input() specialist: any[] = [];
  @Input() isLoading: boolean = false;

  constructor(
    public imageService: ImageService) { }

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