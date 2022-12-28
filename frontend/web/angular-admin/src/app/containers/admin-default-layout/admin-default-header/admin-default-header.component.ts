import { Component, AfterViewInit, OnInit } from '@angular/core';
import { AdminDefaultHeaderBaseComponent } from './admin-default-header-base.component';

@Component({
  selector: 'app-admin-default-header',
  templateUrl: './admin-default-header.component.html',
  styleUrls: ['./admin-default-header.component.scss']
})
export class AdminDefaultHeaderComponent extends AdminDefaultHeaderBaseComponent implements OnInit, AfterViewInit {

  override translations: any = {
    pageTitle: ''
  }

  override ngOnInit(): void {
    this.getTranslations();
    this.listenForEvents();
  }

  public ngAfterViewInit(): void {
    this.initJqueryMenuFn();
  }

  override listenForEvents(): void {
    window.addEventListener('change:language', () => {
      this.getTranslations();
    });
    window.addEventListener('toggled:sidebar', () => {
      this.sideBarOpened = !this.sideBarOpened;
    });
  }

  override getTranslations(): void {
    this.languageService.get('home.pageTitle').subscribe((res: string) => {
      this.translations.pageTitle = res;
    });
  }
}