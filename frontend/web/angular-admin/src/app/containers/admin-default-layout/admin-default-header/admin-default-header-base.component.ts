import { Directive, OnInit } from '@angular/core';
import { User } from 'src/app/generated';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ImageService } from 'src/app/services/image.service';
import { LanguageService } from 'src/app/services/language.service';
import { NavigationService } from 'src/app/services/navigation.service';
import { INavData } from '../../models/menu.model';
import { navItems } from '../_nav';

declare var $: any;

@Directive({ selector: 'directive-admin-default-header-base' })
export class AdminDefaultHeaderBaseComponent implements OnInit {
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
    this.listenForEvents();
  }

  public listenForEvents(): void {
    window.addEventListener('change:language', () => {
      this.getTranslations();
    });
  }

  public getTranslations(): void { }

  public changeLanguage(lang: string): void {
    this.currentLang = lang;
    this.languageService.changeLanguage(lang);
  }

  public initJqueryMenuFn(): void {
    var $wrapper = $('.main-wrapper');
    // Sidebar Initiate
    this.init();

    // Mobile menu sidebar overlay
    $(document).on('click', '#mobile_btn', function () {
      $wrapper.toggleClass('slide-nav');
      $('.sidebar-overlay').toggleClass('opened');
      $('html').addClass('menu-opened');
      return false;
    });

    // Sidebar overlay
    $(".sidebar-overlay").on("click", function () {
      $wrapper.removeClass('slide-nav');
      $(".sidebar-overlay").removeClass("opened");
      $('html').removeClass('menu-opened');
    });

    // Page Content Height
    if ($('.page-wrapper').length > 0) {
      var height = $(window).height();
      $(".page-wrapper").css("min-height", height);
    }

    // Page Content Height Resize
    $(window).resize(function () {
      if ($('.page-wrapper').length > 0) {
        var height = $(window).height();
        $(".page-wrapper").css("min-height", height);
      }
    });

  }

  public init(): void {
    var that = $('#sidebar-menu a');
    $('#sidebar-menu a').on('click', function (e: any) {
      if ($(that).parent().hasClass('submenu')) {
        e.preventDefault();
      }
      if (!$(that).hasClass('subdrop')) {
        $('ul', $(that).parents('ul:first')).slideUp(350);
        $('a', $(that).parents('ul:first')).removeClass('subdrop');
        $(that).next('ul').slideDown(350);
        $(that).addClass('subdrop');
      } else if ($(that).hasClass('subdrop')) {
        $(that).removeClass('subdrop');
        $(that).next('ul').slideUp(350);
      }
    });
    $('#sidebar-menu ul li.submenu a.active').parents('li:last').children('a:first').addClass('active').trigger('click');

    // Sidebar Slimscroll
    var $slimScrolls = $('.slimscroll');
    if ($slimScrolls.length > 0) {
      $slimScrolls.slimScroll({
        height: 'auto',
        width: '100%',
        position: 'right',
        size: '7px',
        color: '#ccc',
        allowPageScroll: false,
        wheelStep: 10,
        touchScrollStep: 100
      });
      var wHeight = $(window).height() - 60;
      $slimScrolls.height(wHeight);
      $('.sidebar .slimScrollDiv').height(wHeight);
      $(window).resize(function () {
        var rHeight = $(window).height() - 60;
        $slimScrolls.height(rHeight);
        $('.sidebar .slimScrollDiv').height(rHeight);
      });
    }
  }

  public toggleSideBar(): void {
    if ($('body').hasClass('mini-sidebar')) {
      $('body').removeClass('mini-sidebar');
      $('.subdrop + ul').slideDown();
    } else {
      $('body').addClass('mini-sidebar');
      $('.subdrop + ul').slideUp();
    }
    this.sideBarOpened = !this.sideBarOpened;
  }

}