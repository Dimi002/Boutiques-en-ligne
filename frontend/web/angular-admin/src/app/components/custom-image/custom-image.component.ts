import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-custom-image',
  templateUrl: './custom-image.component.html',
  styleUrls: ['./custom-image.component.scss'],
})
export class CustomImageComponent implements OnInit {
  public _src: string | undefined = '';
  public defaultSrc: string = 'assets/website/img/stores.jpg';

  @Input() alt: string | undefined = 'image';
  @Input() imgClass: string | undefined = '';
  @Output() onloadend = new EventEmitter<string>();

  constructor() { }

  public ngOnInit() { }

  @Input()
  set src(imageUrl: string) {
    const suffix: string = imageUrl.slice(imageUrl.indexOf('=') + 1);
    if (imageUrl && suffix !== 'undefined' && suffix !== 'null') {
      this.storeImage(imageUrl).then((storedBase64Data) => {
        if (!storedBase64Data || storedBase64Data === 'data:') {
          this._src = this.defaultSrc;
          return;
        }
        if (storedBase64Data) {
          this._src = storedBase64Data;
          this.onloadend.emit(this._src);
        }
      });
    } else {
      this._src = this.defaultSrc;
    }
  }

  public async storeImage(url: string): Promise<string> {
    // Fetch the photo, read as a blob, then convert to base64 format
    const response = await fetch(url);

    // Convert to Blob
    const blob: Blob = await response.blob();

    // Convert base64 data, witch the Filesystem plugin requires
    return Promise.resolve((this.convertBlobToBase64(blob)));
  }

  public convertBlobToBase64(blob: Blob): Promise<string> {
    return new Promise<string>((resolve, reject) => {
      let fileReader = new FileReader();
      if (blob instanceof Blob) {
        const realFileReader = (fileReader as any)._realReader;
        if (realFileReader) {
          fileReader = realFileReader;
        }
      }
      fileReader.onloadend = function () {
        resolve(this.result as string);
      };
      fileReader.onerror = function (err) {
        reject(err);
      };
      fileReader.readAsDataURL(blob);
    });
  }
}
