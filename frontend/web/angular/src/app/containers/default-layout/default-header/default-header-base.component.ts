import { Component} from '@angular/core';
import { TranslationsBaseDirective } from 'src/app/base-components/translations-base.component';
import { LanguageService } from 'src/app/services/language.service';
import { NavigationService } from 'src/app/services/navigation.service';
import { INavData } from '../../models/menu.model';
import { navItems } from '../_nav';

declare var $: any;

@Component({ selector: 'app-default-header-base', template: '' })
export class DefaultHeaderBaseComponent extends TranslationsBaseDirective {
  public navItems: INavData[] = navItems;
  public menuActive: INavData = {};
  public sideBarOpened: boolean = true;

  public constructor(
    override languageService: LanguageService,
    public navService: NavigationService) {
      super(languageService);
  }

  public initJqueryMenuFn(): void {
    $(document).on('click', '#mobile_btn', function () {
      $('.sidebar-overlay').toggleClass('opened');
      $('.main-menu-wrapper').toggleClass('mobile-menu-opened');
      return false;
    });

    this.closeSideMenuInit();
  }

  public closeSideMenuInit(): void {
    // Menu close btn
    $(document).on('click', '#menu_close', function () {
      $('.sidebar-overlay').removeClass('opened');
      $('.main-menu-wrapper').removeClass('slide-nav mobile-menu-opened');
    });
    // Sidebar overlay
    $(document).on('click', '.sidebar-overlay', function () {
      $('.sidebar-overlay').removeClass('opened');
      $('.main-menu-wrapper').removeClass('slide-nav mobile-menu-opened');
    });
  }

  public closeMenu(): void {
    $('.sidebar-overlay').toggleClass('opened');
    $('.main-menu-wrapper').toggleClass('mobile-menu-opened');
  }

}