import { Component } from '@angular/core';
import { NavigationService } from 'src/app/services/navigation.service';
import { LanguageService } from 'src/app/services/language.service';
import { BreadcrumbService } from '../../services/breadcrumb.service';
import { Breadcrumb } from 'src/app/additional-models/breadcrumb.model';

@Component({
  selector: 'app-page-banner-breadcrumb',
  templateUrl: './page-banner-breadcrumb.component.html',
  styleUrls: ['./page-banner-breadcrumb.component.scss']
})
export class PageBannerBreadcrumbComponent {

  breadcrumbs?: Breadcrumb[] = []; 
  public activeMenuTitle?: string = '';
 
  constructor(
    private readonly breadcrumbService: BreadcrumbService,
    public languageService: LanguageService,
    public navService: NavigationService) { 
    breadcrumbService.breadcrumbs$.subscribe(res => {
      this.breadcrumbs = res;
    }); 
  }
}
