import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { ImageCropperModalComponent } from 'src/app/components/image-cropper-modal/image-cropper-modal.component';

@Component({
  selector: 'app-profile-settings-form',
  templateUrl: './profile-settings-form.component.html',
  styleUrls: ['./profile-settings-form.component.scss']
})
export class ProfileSettingsFormComponent implements OnInit, AfterViewInit {
  public form: FormGroup = this.formBuilder.group({});
  public errorMessages: any = {};

  public profileImage!: File;
  public profileImageUrl: string = '';

  constructor(
    private formBuilder: FormBuilder,
    public modalService: BsModalService) {}

  ngOnInit(): void {
    this.initForm();
  }

  public ngAfterViewInit(): void {}

  public initForm(): void {
    this.form = this.formBuilder.group({
      firstName: ['Richard', [ Validators.required ] ],
      lastName: ['Wilson', [ Validators.required ] ],
      birthDate: ['1983-07-24', [ Validators.required ] ],
      bloodGroup: ['O+', [ Validators.required ] ],
      email: ['richard@example.com',[ Validators.required, Validators.email ] ],
      phone: ['+1 202-555-0125', [ Validators.required, Validators.minLength(9), Validators.maxLength(13) ] ],
      address: ['806 Twin Willow Lane', [ Validators.required ] ],
      city: ['Old Forge', [ Validators.required ] ],
      country: ['United States', [ Validators.required ] ],
      profileImage: [null, [ Validators.required ] ]
    });

    this.errorMessages = {
      email: [
        { type: 'required', message: 'the email address is required' },
        { type: 'email', message: 'enter a valid email address' },
      ],
      phoneOM: [
        { type: 'required', message: 'the phone is required' },
        { type: 'minlength', message: 'the phone must have a minimum of 9 characters' },
        { type: 'maxlength', message: 'the phone must have a maximum of 13 characters' },
      ],
      phoneMOMO: [
        { type: 'required', message: 'the phone is required' },
        { type: 'minlength', message: 'the phone must have a minimum of 9 characters' },
        { type: 'maxlength', message: 'the phone must have a maximum of 13 characters' },
      ],
      CNI: [
        { type: 'required', message: 'the CNI number is required' },
        { type: 'minlength', message: 'CNI number must be at least 9 characters long' }
      ]
    }
  }

  public chooseFile(id: string): void {
    const element: HTMLElement | null = document.getElementById(id);
    if (element)
      element.click();
    return;
  }

  public selectFile(event: any): void {
    if (event.target.files && event.target.files.length > 0) {
      this.openCroppingModal(event);
    }
  }

  public openCroppingModal(event: any): void {
    const initialState = { imageChangedEvent: event };
    const modalRef: BsModalRef = this.modalService.show(ImageCropperModalComponent, {
      initialState, class: "edit-time-slot-modal modal-md modal-dialog-centered", ignoreBackdropClick: true
    });
    if (modalRef.onHidden) {
      modalRef.onHidden.subscribe(() => {
        const croppedImageBase64: any = modalRef.content.croppedImageBase64;
        const croppedImageFile: any = modalRef.content.croppedImageFile;
        if (croppedImageBase64 && croppedImageFile) {
          this.profileImageUrl = croppedImageBase64;
          this.profileImage = croppedImageFile;
        }
      });
    }
  }

  public saveChanges(): void {
    alert('this.form.value: \n\n' + JSON.stringify(this.form.value));
  }
}
