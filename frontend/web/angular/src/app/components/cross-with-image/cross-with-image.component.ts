import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-cross-with-image',
  templateUrl: './cross-with-image.component.html',
  styleUrls: ['./cross-with-image.component.scss']
})
export class CrossWithImageComponent {
  @Input() bgImgClass: string | undefined = '';
  @Input() bgSrc: string | undefined = '';

  constructor() { }

}
