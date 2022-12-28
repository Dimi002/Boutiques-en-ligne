import { Directive } from '@angular/core';

declare var $: any;

@Directive({ selector: 'directive-profile-total' })
export class ProfileTotalBaseComponent {

  constructor() { }

  public initJQuerryFn(): void {
    if ($('.circle-bar').length > 0) {
      this.animateElements();
    }
    $(window).scroll(this.animateElements);
  }

  public animateElements(): void {
    $('.circle-bar1').each(function () {
      var that: string = '.circle-bar1';
      var elementPos = $(that).offset().top;
      var topOfWindow = $(window).scrollTop();
      var percent = $(that).find('.circle-graph1').attr('data-percent');
      var animate = $(that).data('animate');
      if (elementPos < topOfWindow + $(window).height() - 30 && !animate) {
        $(that).data('animate', true);
        $(that).find('.circle-graph1').circleProgress({
          value: percent / 100,
          size: 400,
          thickness: 30,
          fill: {
            color: '#da3f81'
          }
        });
      }
    });
    $('.circle-bar2').each(function () {
      var that: string = '.circle-bar2';
      var elementPos = $(that).offset().top;
      var topOfWindow = $(window).scrollTop();
      var percent = $(that).find('.circle-graph2').attr('data-percent');
      var animate = $(that).data('animate');
      if (elementPos < topOfWindow + $(window).height() - 30 && !animate) {
        $(that).data('animate', true);
        $(that).find('.circle-graph2').circleProgress({
          value: percent / 100,
          size: 400,
          thickness: 30,
          fill: {
            color: '#68dda9'
          }
        });
      }
    });
    $('.circle-bar3').each(function () {
      var that: string = '.circle-bar3';
      var elementPos = $(that).offset().top;
      var topOfWindow = $(window).scrollTop();
      var percent = $(that).find('.circle-graph3').attr('data-percent');
      var animate = $(that).data('animate');
      if (elementPos < topOfWindow + $(window).height() - 30 && !animate) {
        $(that).data('animate', true);
        $(that).find('.circle-graph3').circleProgress({
          value: percent / 100,
          size: 400,
          thickness: 30,
          fill: {
            color: '#1b5a90'
          }
        });
      }
    });
  }

}
