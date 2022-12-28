import { Component, AfterViewInit, OnInit } from '@angular/core';
import { DoctorDefaultHeaderBaseComponent } from './doctor-default-header-base.component';

@Component({
  selector: 'app-doctor-default-header',
  templateUrl: './doctor-default-header.component.html',
  styleUrls: ['./doctor-default-header.component.scss']
})
export class DoctorDefaultHeaderComponent extends DoctorDefaultHeaderBaseComponent implements OnInit, AfterViewInit {
  public ngAfterViewInit(): void {
    this.initJqueryMenuFn();
  }

  override getTranslations(): void {
    this.languageService.get('home.pageTitle').subscribe((res: string) => {
      this.translations.pageTitle = res;
    });
  }

}