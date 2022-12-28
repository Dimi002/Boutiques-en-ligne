import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { SocialMediaLinks, SpecialistControllerService, User } from 'src/app/generated';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { NotificationService } from 'src/app/services/notification.service';
import { EXCEPTION } from 'src/app/utils/constants';

@Component({
  selector: 'app-social-media-form',
  templateUrl: './social-media-form.component.html',
  styleUrls: ['./social-media-form.component.scss']
})
export class SocialMediaFormComponent implements OnInit {
  public form: FormGroup = this.formBuilder.group({});

  public isLoading: boolean = false;
  public id: number = Number(this.authService.getUser()?.specialist?.specialistId);
  public socialToSet: SocialMediaLinks = {};
  public socialToGet: SocialMediaLinks = {};

  constructor(
    private formBuilder: FormBuilder,
    public specialistControllerService: SpecialistControllerService,
    public authService: AuthenticationService,
    public notificationService: NotificationService
  ) { }

  public ngOnInit(): void {
    this.initForm();
    this._init();
  }

  public initForm(): void {
    this.form = this.formBuilder.group({
      facebook: new FormControl('', []),
      twitter: new FormControl('', []),
      instagram: new FormControl('', []),
      pinterest: new FormControl('', []),
      linkedin: new FormControl('', []),
      youtube: new FormControl('', []),
    });
  }

  public _init(): void {
    this.specialistControllerService.getSocialMediaByIdUsingGET(this.id).subscribe(
      res => {
        this.socialToGet = res;
        this.form.patchValue({
          facebook: this.socialToGet.facebook,
          twitter: this.socialToGet.twitter,
          instagram: this.socialToGet.instagram,
          pinterest: this.socialToGet.pinterest,
          linkedin: this.socialToGet.linkedin,
          youtube: this.socialToGet.youtube
        });
      },
      err => {
        if (err.stringErrorCode == 500 && err.errorText == "Specialist is not found") {
          this.notificationService.danger("Sorry, you have not been found");
        } else {
          this.notificationService.danger("An unknown error has occurred, check your internet connection");
        }
      },
      () => { this.isLoading = false }
    );
  }

  public saveChanges(): void {
    this.socialToSet = this.form.value;
    this.isLoading = true;
    this.specialistControllerService.updateSocialMediaByIdUsingPOST(this.socialToSet, this.id).subscribe(
      res => {
        this.notificationService.success("Information successfully modified");
      },
      err => {
        if (err.stringErrorCode == 501 && err.errorText == EXCEPTION.SPECIALIST_NOT_FOUND) {
          this.notificationService.danger(EXCEPTION.SPECIALIST_NOT_FOUND);
        } else {
          this.notificationService.danger(EXCEPTION.NO_INTERNET_CONNECTION);
        }
      },
      () => { this.isLoading = false }
    );
  }
}
