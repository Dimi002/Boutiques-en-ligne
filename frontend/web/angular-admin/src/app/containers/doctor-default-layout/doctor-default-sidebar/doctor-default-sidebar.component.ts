import { AfterViewInit, Component, Input, OnInit } from '@angular/core';
import { LanguageService } from 'src/app/services/language.service';
import { NavigationService } from 'src/app/services/navigation.service';
import { INavData } from '../../models/menu.model';
import { DoctorDefaultHeaderBaseComponent } from '../doctor-default-header/doctor-default-header-base.component';

@Component({
  selector: 'app-doctor-default-sidebar',
  templateUrl: './doctor-default-sidebar.component.html',
  styleUrls: ['./doctor-default-sidebar.component.scss']
})
export class DoctorDefaultSidebarComponent extends DoctorDefaultHeaderBaseComponent implements OnInit { }
