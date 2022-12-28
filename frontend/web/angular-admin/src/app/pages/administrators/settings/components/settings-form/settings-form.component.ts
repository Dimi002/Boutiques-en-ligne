import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { ImageCropperModalComponent } from 'src/app/components/image-cropper-modal/image-cropper-modal.component';
import { Setting, SettingControllerService, SettingDTO, UploadResponse } from 'src/app/generated';
import { ImageService } from 'src/app/services/image.service';
import { NotificationService } from 'src/app/services/notification.service';
import { EXCEPTION } from 'src/app/utils/constants';

@Component({
  selector: 'app-settings-form',
  templateUrl: './settings-form.component.html',
  styleUrls: ['./settings-form.component.scss']
})
export class SettingsFormComponent implements OnInit {
  public form: FormGroup = this.formBuilder.group({});
  public errorMessages: any = {};
  public setting?: SettingDTO;

  public logo!: File;
  public logoUrl: string = '';

  public cover!: File;
  public coverUrl: string = '';
  public coverImage = '';


  isLoading: Boolean = false;
  public loadningImage = '';

  constructor(
    private formBuilder: FormBuilder,
    public modalService: BsModalService,
    public settingService: SettingControllerService,
    public imageService: ImageService,
    public notificationService: NotificationService) { }

  ngOnInit(): void {
    this._initialization();
    this.initForm();
  }

  public initForm(): void {
    this.form = this.formBuilder.group({
      logo: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      email2: ['', [Validators.email]],
      adresse: ['', [Validators.required]],
      tel: ['', [Validators.required, Validators.minLength(9), Validators.maxLength(13)]],
      tel2: ['', [Validators.minLength(9), Validators.maxLength(13)]],
      ln: [''],
      fb: [''],
      twitter: [''],
      insta: [''],
      planingStartAt: ['', [Validators.required]],
      planingEndAt: ['', [Validators.required]],
      video: [''],
      videoCover: ['']
    });
    if (this.setting) {
      this.form = this.formBuilder.group({
        id: this.setting?.id,
        logo: this.setting?.logo,
        email: this.setting?.email,
        email2: this.setting?.email2,
        adresse: this.setting?.adresse,
        tel: this.setting?.tel,
        tel2: this.setting?.tel2,
        ln: this.setting?.ln,
        fb: this.setting?.fb,
        twitter: this.setting?.twitter,
        insta: this.setting?.insta,
        planingStartAt: this.setting?.planingStartAt,
        planingEndAt: this.setting?.planingEndAt,
        video: this.setting?.video,
        videoCover: this.setting?.videoCover
      });
      this.loadningImage = this.imageService.getCover(this.setting?.logo);
      this.coverImage = this.imageService.getCover(this.setting?.videoCover);
    }
  }

  get f() {
    return this.form.controls;
  }

  public chooseFile(id: string): void {
    const element: HTMLElement | null = document.getElementById(id);
    if (element)
      element.click();
    return;
  }

  public selectFile(event: any, type = 0): void {
    if (event.target.files && event.target.files.length > 0) {
      this.openCroppingModal(event, type);
    }
  }

  public openCroppingModal(event: any, type: number): void {
    const initialState = { imageChangedEvent: event };
    const modalRef: BsModalRef = this.modalService.show(ImageCropperModalComponent, {
      initialState, class: "edit-time-slot-modal modal-md modal-dialog-centered", ignoreBackdropClick: true
    });
    if (modalRef.onHidden) {
      modalRef.onHidden.subscribe(() => {
        const croppedImageBase64: any = modalRef.content.croppedImageBase64;
        const croppedImageFile: any = modalRef.content.croppedImageFile;
        if (croppedImageBase64 && croppedImageFile) {
          this.logoUrl = croppedImageBase64;
          this.logo = croppedImageFile;
          this.imageService.upload([this.logo]).toPromise().then(res => {
            const result = res as UploadResponse[];
            if (type == 0) {
              this.f['logo'].setValue(result[0]?.uploadLocation);
              this.loadningImage = this.imageService.getCover(result[0]?.uploadLocation);
            } else {
              this.f['videoCover'].setValue(result[0]?.uploadLocation);
              this.coverImage = this.imageService.getCover(result[0]?.uploadLocation);
            }
          })
        }
      });
    }
  }

  public saveChanges(): void {
    const data: SettingDTO = this.form.value;
    this.isLoading = true;
    this.settingService.createUsingPOST2(data).toPromise().then(
      (res?: Setting) => {
        this.notificationService.success('Paramètres enregistrés avec succes!!')
      }
    ).catch(error => {
      if (error && error?.errorText == 'The setting must not be null or have empty values')
        this.notificationService.success('The setting must not be null or have empty values')
    })
      .finally(() => {
        this.isLoading = false;
      })

  }

  async _initialization() {
    await this.settingService.recordsUsingGET3().toPromise().then((res?: Setting) => {
      this.setting = res;
      this.initForm();
    }).catch(error => {
      if (error && error?.errorText == 'The setting is null')
        this.notificationService.success('The setting is null')
    })
  }
}
