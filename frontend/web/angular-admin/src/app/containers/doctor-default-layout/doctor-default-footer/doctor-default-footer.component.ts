import { Component } from '@angular/core';
import { NavigationService } from 'src/app/services/navigation.service';

@Component({
  selector: 'app-doctor-default-footer',
  templateUrl: './doctor-default-footer.component.html',
  styleUrls: ['./doctor-default-footer.component.scss'],
})
export class DoctorDefaultFooterComponent {
  constructor(
    public navService: NavigationService) {}
}
