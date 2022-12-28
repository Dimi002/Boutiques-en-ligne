import { Component, OnInit } from '@angular/core';
import { base64ToFile, ImageCroppedEvent, LoadedImage, OutputFormat } from 'ngx-image-cropper';
import { ModalBaseComponent } from '../base/modal-base.component';

@Component({
  selector: 'app-image-cropper-modal',
  templateUrl: './image-cropper-modal.component.html',
  styleUrls: ['./image-cropper-modal.component.scss']
})
export class ImageCropperModalComponent extends ModalBaseComponent implements OnInit {
  public imageChangedEvent: any = '';
  public croppedImageBase64: string | null | undefined = '';
  public croppedImageBlob!: Blob;
  public croppedImageFile!: File;

  public croppedImageFormat: OutputFormat = 'png';
  public resizeToWidth: number = 400;
  public resizeToHeight: number = 400;

  public ngOnInit(): void {
  }

  public imageCropped(event: ImageCroppedEvent): void {
    this.croppedImageBase64 = event.base64;
    if (this.croppedImageBase64) {
      this.croppedImageBlob = base64ToFile(this.croppedImageBase64);
      this.croppedImageFile = this.blobToFile(this.croppedImageBlob, new Date().getTime().toString() + '.' + this.croppedImageFormat);
    }
  }

  public imageLoaded(image: LoadedImage): void { }

  public cropperReady(): void { }

  public loadImageFailed(): void { }

  public saveChanges(): void {
    this.close();
  }

  private blobToFile(theBlob: any, fileName: string): File {
    theBlob.lastModifiedDate = new Date();
    theBlob.name = fileName;
    return <File>theBlob;
  };

}
