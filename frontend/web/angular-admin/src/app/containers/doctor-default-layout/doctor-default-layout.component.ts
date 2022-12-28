import { AfterViewInit, Component } from '@angular/core';
import { DoctorDefaultBaseLayoutComponent } from './doctor-default-layout-base.component';

@Component({
  selector: 'app-doctor-default-layout',
  templateUrl: './doctor-default-layout.component.html',
})
export class DoctorDefaultLayoutComponent extends DoctorDefaultBaseLayoutComponent implements AfterViewInit {
  constructor() {
    super();
  }

  public ngAfterViewInit(): void {
    this.initJQuerryFn();
  }
}
