import { Component, OnInit } from '@angular/core';
import { INavData } from '../../models/menu.model';
import { DoctorDefaultHeaderBaseComponent } from '../doctor-default-header/doctor-default-header-base.component';
import { navItems } from '../_nav';

@Component({
  selector: 'app-doctor-profile-sidebar',
  templateUrl: './doctor-profile-sidebar.component.html',
  styleUrls: ['./doctor-profile-sidebar.component.scss']
})
export class DoctorProfileSidebarComponent extends DoctorDefaultHeaderBaseComponent implements OnInit {
  public override menuActive: INavData = (navItems && navItems[0] && navItems[0].children && navItems[0].children[0]) ? navItems[0].children[0] : {};
}
