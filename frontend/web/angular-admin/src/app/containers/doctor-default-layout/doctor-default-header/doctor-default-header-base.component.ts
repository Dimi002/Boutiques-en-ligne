import { Directive, OnInit } from '@angular/core';
import { User } from 'src/app/generated/model/user';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ImageService } from 'src/app/services/image.service';
import { LanguageService } from 'src/app/services/language.service';
import { NavigationService } from 'src/app/services/navigation.service';
import { INavData } from '../../models/menu.model';
import { navItems } from '../_nav';

declare var $: any;

@Directive({ selector: 'directive-doctor-default-header' })
export class DoctorDefaultHeaderBaseComponent implements OnInit {
  public navItems: INavData[] = navItems;
  public menuActive: INavData = {};
  public sideBarOpened: boolean = true;

  public currentLang: string = this.languageService.deviceLanguage;

  public translations: any = {};

  public currentUser: User = this.authService.getUser();

  public specialitiesListAsString: string = String(this.currentUser?.specialist?.specialitiesList?.join(', '));

  public specialitiesList: string[] = this.currentUser?.specialist?.specialitiesList!;

  public userImagePath: string = this.imageService.getCover(this.currentUser?.userImagePath!);

  public constructor(
    public languageService: LanguageService,
    public navService: NavigationService,
    public authService: AuthenticationService,
    public imageService: ImageService) {
  }

  public ngOnInit(): void {
    this.getTranslations();
    this.listenForLoginEvents();
  }

  public listenForLoginEvents(): void {
    window.addEventListener('change:language', () => {
      this.getTranslations();
    });
    window.addEventListener('profile-image:change', () => {
      this.loadUserAgain();
    });
    window.addEventListener('profile-speciality:change', () => {
      this.specialitiesListAsString = String(this.currentUser?.specialist?.specialitiesList?.join(', '));
    });
  }

  public loadUserAgain(): void {
    this.currentUser = this.authService.getUser();
    this.userImagePath = this.imageService.getCover(this.currentUser?.userImagePath!);
  }

  public getTranslations(): void { }

  public changeLanguage(lang: string): void {
    this.currentLang = lang;
    this.languageService.changeLanguage(lang);
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