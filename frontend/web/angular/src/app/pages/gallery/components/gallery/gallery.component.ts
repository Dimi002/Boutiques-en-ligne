import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Image } from '../index/index.component';

@Component({
  selector: 'app-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.scss']
})
export class GalleryComponent {
  @Output() gallerySelected: EventEmitter<Image[]> = new EventEmitter<any>();
  @Input() galleryData!: Image[];
  @Input() showCount = true;

  constructor() {}
  
  public onSelectGallery(event: Image[]): void {
    this.gallerySelected.emit(event);
  }

}
