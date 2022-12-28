import { Component, OnDestroy } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ImageService } from 'src/app/services/image.service';
declare var $: any;

@Component({
  selector: 'app-video-ligthbox',
  templateUrl: './video-ligthbox.component.html',
  styleUrls: ['./video-ligthbox.component.scss']
})
export class VideoLigthboxComponent implements OnDestroy {
  public videoCoverUrl: string = '/assets/website/img/img-01.png';
  public videoUrl: string = 'https://www.youtube.com/embed/NY9O-QrZTZU';

  constructor(public authService: AuthenticationService,
    public imageService: ImageService) { }

  public ngOnInit(): void {
    const setting = this.authService.getSettings();
    this.videoCoverUrl = setting?.videoCover ? this.imageService.getCover(setting.videoCover) : this.videoCoverUrl;
    this.videoUrl = setting?.video ?? this.videoUrl;
  }

  public onPreviewVideo(): void {
    $('body').append(
      '<div class="video-lightbox">' +
      '<button class="close-btn" (click)="onClosePreview()" id="video-close-btn">' +
      '<i class="fa fa-times"></i>' +
      '</button>' +
      '<div class="lightbox-video">' +
      '<iframe src=' + this.videoUrl + ' title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>' +
      '</div>' +
      '</div>'
    );
    setTimeout(() => {
      $('.video-lightbox').addClass('opened');
    }, 100);
    $(document).on('click', '#video-close-btn', function () {
      $('.video-lightbox').removeClass('opened');
      setTimeout(() => {
        $('.video-lightbox').remove();
      }, 1000);
    });
  }

  public ngOnDestroy(): void {
    $('.video-lightbox').remove();
  }

}
