import { Directive } from '@angular/core';

declare var $: any;

@Directive({ selector: 'directive-doctor-default-base-layout' })
export class DoctorDefaultBaseLayoutComponent {

  constructor() {}

  public initJQuerryFn(): void {
    // Stick Sidebar
    if ($(window).width() > 767) {
      if ($('.theiaStickySidebar').length > 0) {
        $('.theiaStickySidebar').theiaStickySidebar({
          // Settings
          additionalMarginTop: 30
        });
      }
    }
  }
}
