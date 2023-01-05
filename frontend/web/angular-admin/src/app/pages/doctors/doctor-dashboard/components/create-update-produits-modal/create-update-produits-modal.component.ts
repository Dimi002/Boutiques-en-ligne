import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { ImageCropperModalComponent } from 'src/app/components/image-cropper-modal/image-cropper-modal.component';
import { Role, RoleControllerService, SearchCriteriasModel, Specialist, SpecialistControllerService, SpecialistSpecialityControllerService, SpecialistSpecialityMin, User, UserControllerService } from 'src/app/generated';
import { MultiselecItem } from 'src/app/models';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { DateParserService } from 'src/app/services/date-parser.service';
import { ImageService } from 'src/app/services/image.service';
import { NavigationService } from 'src/app/services/navigation.service';
import { NotificationService } from 'src/app/services/notification.service';
import { EXCEPTION } from 'src/app/utils/constants';

@Component({
  selector: 'app-create-update-produits-modal',
  templateUrl: './create-update-produits-modal.component.html',
  styleUrls: ['./create-update-produits-modal.component.scss']
})
export class CreateUpdateProduitsModalComponent implements OnInit {


  public inputValue: string = '';
  public inputLabel: string = '';
  public inputPlaceholder: string = 'Saisir le nom d\'une categori ici...';
  public inputPlaceholderSpecilisation: string = 'Enter Specialisation';
  public machtList: MultiselecItem[] = [];
  public selectedItems: MultiselecItem[] = [];
  public copySelectedItems: MultiselecItem[] = [];
  private searchCriterias: SearchCriteriasModel[] = [];
  public specialistSpeciality: SpecialistSpecialityMin = {};
  public isLoading: boolean = false;
  public isError: boolean = false;
  public isSuccess: boolean = false;
  public specialistId!: number;
  public specialityId!: number;
  public id: any;
  public us: User = {};
  public specialistToUpdate: Specialist = {};
  public userToUpdate = this.authService.getUser();
  public userImage: { file: File | null, url: string } = {
    file: null,
    url: ''
  };

  public errorMessages: any = {};
  public biography: string = '';

  public profileImage!: File;
  public profileImageUrl: string = '';
  public uploadImageUrl: string = '';

  public educations: { degree: string, college: string, yearOfCompletion: string }[] = [
    { degree: '', college: '', yearOfCompletion: '' }
  ];
  public experiences: { hospitalName: string, from: string, to: string, designation: string }[] = [
    { hospitalName: '', from: '', to: '', designation: '' }
  ];
  public awards: { award: string, year: string, description: string }[] = [
    { award: '', year: '', description: '' }
  ];

  public mode?: string;
  public form: FormGroup = this.fb.group({
    roleName: ['', Validators.required],
    roleDesc: ['', Validators.required]
  });

  public role: Role = {};

  constructor(
    public modalService: BsModalRef,
    public rolesService: RoleControllerService,
    private fb: FormBuilder,
    private formBuilder: FormBuilder,
    public modalServices: BsModalService,
    public specialityControllerService: SpecialistSpecialityControllerService,
    public notificationService: NotificationService,
    public userService: UserControllerService,
    public navigationService: NavigationService,
    public specialistService: SpecialistControllerService,
    public authService: AuthenticationService,
    public imageUploadService: ImageService,

    public dateService: DateParserService
  ) { }

  ngOnInit(): void {
    this.initForm();
  }

  get f() {
    return this.form.controls;
  }

  public initForm(): void {
    this.form = this.fb.group({
      roleId: [],
      roleName: ['', Validators.required],
      roleDesc: ['', Validators.required]
    });

    if (this.mode === "UPDATE") {
      this.form.patchValue({
        roleId: this.role?.roleId,
        roleName: this.role?.roleName,
        roleDesc: this.role?.roleDesc,
      });
    }
  }

  public create(): void {
    if (this.authService.hasAnyPermission(['CREATE_ROLE'])) {
      const item: Role = this.form.value;
      this.role = item;
      this.isLoading = true;
      this.rolesService.createRoleUsingPOST(this.role).toPromise().then(
        res => {
          if (res) {
            this.isSuccess = true;
          }
        }
      ).catch(
        error => {
          if (error.stringErrorCode == 501 && error.errorText == EXCEPTION.ROLE_NAME_ALREADY_EXIST) {
            this.notificationService.danger(EXCEPTION.ROLE_NAME_ALREADY_EXIST);
          }
          else if (error.stringErrorCode == 502 && error.errorText == EXCEPTION.ROLE_ALREADY_DELETED) {
            this.notificationService.danger(EXCEPTION.ROLE_ALREADY_DELETED);
          }
          else if (error.stringErrorCode == 503 && error.errorText == EXCEPTION.ROLE_ALREADY_DEACTIVATED) {
            this.notificationService.danger(EXCEPTION.ROLE_ALREADY_DEACTIVATED);
          }
          else {
            this.isError = true;
          }
        }
      ).finally(
        () => {
          this.isLoading = false;
          this.close();
        }
      )
    } else {
      this.notificationService.danger(EXCEPTION.NO_AUTORISATION_ROLE_CREATED);
    }
  }

  public update(): void {
    if (this.authService.hasAnyPermission(['UPDATE_ROLE_INFOS'])) {
      const item: Role = this.form.value;
      this.role.roleName = item.roleName;
      this.role.roleDesc = item.roleDesc;
      this.isLoading = true;
      this.rolesService.updateRoleUsingPOST(this.role!).toPromise().then(
        res => {
          if (res) {
            this.isSuccess = true;
          }
        }
      ).catch(
        error => {
          if (error.stringErrorCode == 501 && error.errorText == EXCEPTION.ROLE_NAME_ALREADY_EXIST) {
            this.notificationService.danger(EXCEPTION.ROLE_NAME_ALREADY_EXIST);
          } else if (error.stringErrorCode == 502 && error.errorText == EXCEPTION.ROLE_WAS_DELETED) {
            this.notificationService.danger(EXCEPTION.ROLE_WAS_DELETED)
          } else if (error.stringErrorCode == 503 && error.errorText == EXCEPTION.ROLE_WAS_DESACTIVED) {
            this.notificationService.danger(EXCEPTION.ROLE_WAS_DESACTIVED)
          }
          else {
            this.isError = true;
          }
        }
      ).finally(
        () => {
          this.isLoading = false;
          this.close();
        }
      )
    } else {
      this.notificationService.danger(EXCEPTION.NO_AUTORISATION_CHANGE_ROLE);
    }
  }

  public close(): void {
    this.modalService.hide();
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

  public validateFile(file: File): boolean {
    const pattern: string[] = ['image/png', 'image/jpg', 'image/jpeg'];
    return pattern.includes(file.type);
  }


  public openCroppingModal(event: any): void {
    const initialState = { imageChangedEvent: event };
    const modalRef: BsModalRef = this.modalServices.show(ImageCropperModalComponent, {
      initialState, class: "edit-time-slot-modal modal-md modal-dialog-centered", ignoreBackdropClick: true
    });
    if (modalRef.onHidden) {
      modalRef.onHidden.subscribe(() => {
        const croppedImageBase64: any = modalRef.content.croppedImageBase64;
        const croppedImageFile: any = modalRef.content.croppedImageFile;
        if (croppedImageBase64 && croppedImageFile) {
          this.profileImageUrl = croppedImageBase64;
          this.profileImage = croppedImageFile;
          this.uploadProfileImage(this.profileImage);
        }
      });
    }
  }

  public dispachProfileImageChangeEvent(): void {
    window.dispatchEvent(new CustomEvent('profile-image:change'));
  }

  public uploadProfileImage(image: File): void {
    this.imageUploadService.upload([image]).toPromise().then(
      res => {
        if (res) {
          this.uploadImageUrl = res[0].uploadLocation!;

          // on recupere l'addresse sur le serveur
          const imgCompletePath: string = this.imageUploadService.getCover(this.uploadImageUrl);

          //  on recupere l'image en base64
          this.imageUploadService.getData(imgCompletePath)
            .subscribe(
              imgData => {
                this.profileImageUrl = imgData;
                this.userService.updateImageUsingPOST(this.uploadImageUrl, this.userToUpdate.id!).toPromise().finally(
                  () => {
                    this.notificationService.success('Votre photo de profile a été modifiée avec succès !');
                  });
              })
          this.userToUpdate.userImagePath = this.uploadImageUrl;
          this.authService.storeUser(this.userToUpdate);
          this.dispachProfileImageChangeEvent();
        }
      }
    )
  }







}

