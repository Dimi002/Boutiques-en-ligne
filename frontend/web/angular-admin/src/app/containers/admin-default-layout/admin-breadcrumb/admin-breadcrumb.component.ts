import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { NavigationService } from 'src/app/services/navigation.service';
import { LanguageService } from 'src/app/services/language.service';
import { Breadcrumb } from '../../models/breadcrumb.model';
import { BreadcrumbService } from '../../services/breadcrumb.service';

@Component({
  selector: 'app-admin-breadcrumb',
  templateUrl: './admin-breadcrumb.component.html',
  styleUrls: ['./admin-breadcrumb.component.scss']
})
export class AdminBreadcrumbComponent implements OnInit {

  breadcrumbs: Breadcrumb[] = []; 
  public activeMenuTitle: string = '';
 
  constructor(
    private readonly breadcrumbService: BreadcrumbService,
    public languageService: LanguageService,
    public navService: NavigationService) { 
    breadcrumbService.breadcrumbs$.subscribe(res => {
      this.breadcrumbs = res;
    }); 
  } 

  ngOnInit(): void {}
}
