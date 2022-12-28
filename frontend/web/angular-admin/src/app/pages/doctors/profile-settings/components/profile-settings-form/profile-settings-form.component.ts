import { MultiselecItem } from 'src/app/models';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { ImageCropperModalComponent } from 'src/app/components/image-cropper-modal/image-cropper-modal.component';
import { NotificationService } from 'src/app/services/notification.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { NavigationService } from 'src/app/services/navigation.service';
import { DateParserService } from 'src/app/services/date-parser.service';
import { ImageService } from 'src/app/services/image.service';
import { SearchCriteriasModel, Specialist, SpecialistControllerService, SpecialistSpecialityControllerService, SpecialistSpecialityMin, Speciality, User, UserControllerService } from 'src/app/generated';
import { EXCEPTION } from 'src/app/utils/constants';

@Component({
  selector: 'app-profile-settings-form',
  templateUrl: './profile-settings-form.component.html',
  styleUrls: ['./profile-settings-form.component.scss']
})
export class ProfileSettingsFormComponent implements OnInit, AfterViewInit {


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

  public form: FormGroup = this.formBuilder.group({});
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

  constructor(
    private formBuilder: FormBuilder,
    public modalService: BsModalService,
    public specialityControllerService: SpecialistSpecialityControllerService,
    public notificationService: NotificationService,
    public userService: UserControllerService,
    public navigationService: NavigationService,
    public specialistService: SpecialistControllerService,
    public authService: AuthenticationService,
    public imageUploadService: ImageService,

    public dateService: DateParserService) { }

  ngOnInit(): void {
    this._init();
    this.initForm();
    this.specialityToDisplay();
  }


  public _init(): void {
    this.specialistId = Number(this.userToUpdate.specialist?.specialistId);
    this.specialistService.findSpecialistByIdUsingGET(this.specialistId).toPromise()
      .then(res => {
        this.biography = res?.biography;
        this.specialistToUpdate = res;
        this.initForm();

      })
      .catch(err => {

      });
  }

  public ngAfterViewInit(): void { }

  public async initForm() {

    if (this.userToUpdate.userImagePath) {
      this.imageUploadService.getData(this.imageUploadService.getCover(this.userToUpdate.userImagePath)).subscribe(path => {
        this.profileImageUrl = path;
      });
    }

    const birthDate: Date = new Date(this.userToUpdate.birthDate + "");

    this.form = this.formBuilder.group({
      basicInfos: new FormGroup({
        username: new FormControl({ value: this.userToUpdate.username, disabled: true }),
        email: new FormControl({ value: this.userToUpdate.email, disabled: true }, [Validators.email]),
        firstName: new FormControl(this.userToUpdate.firstName, []),
        lastName: new FormControl(this.userToUpdate.lastName, []),
        phone: new FormControl(this.userToUpdate.phone?.slice(3), [Validators.minLength(9), Validators.maxLength(13)]),
        profileImage: new FormControl(null, []),
        birthDate: new FormControl(this.dateService.formatYYYYMMDDDate(birthDate), [])
      }),

      contactInfos: new FormGroup({
        city: new FormControl(this.specialistToUpdate.city, []),
        gender: new FormControl(this.specialistToUpdate.gender, []),
        yearOfExperience: new FormControl(this.specialistToUpdate.yearOfExperience, []),
        biography: new FormControl(this.specialistToUpdate.biography, []),
      }),

      spacializationInfos: new FormGroup({
        spacializations: new FormControl('Children Care,Dental Care', [])
      })
    });

    this.errorMessages = {
      email: [
        { type: 'required', message: 'l\'adresse mail est obligatoire' },
        { type: 'email', message: 'entrez une adresse email valide' },
      ],
      phoneOM: [
        { type: 'required', message: 'le téléphone est obligatoire' },
        { type: 'minlength', message: 'le téléphone doit avoir minimum 9 caractères' },
        { type: 'maxlength', message: 'le téléphone doit avoir maximum 13 caractères' },
      ],
      phoneMOMO: [
        { type: 'required', message: 'le téléphone est obligatoire' },
        { type: 'minlength', message: 'le téléphone doit avoir minimum 9 caractères' },
        { type: 'maxlength', message: 'le téléphone doit avoir maximum 13 caractères' },
      ],
      CNI: [
        { type: 'required', message: 'le numéro de CNI est obligatoire' },
        { type: 'minlength', message: 'le numéro de CNI doit avoir minimum 9 caractères' }
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

  public validateFile(file: File): boolean {
    const pattern: string[] = ['image/png', 'image/jpg', 'image/jpeg'];
    return pattern.includes(file.type);
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
          this.uploadProfileImage(this.profileImage);
        }
      });
    }
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

  public dispachProfileImageChangeEvent(): void {
    window.dispatchEvent(new CustomEvent('profile-image:change'));
  }

  public saveChanges(): void {
    const item: User = this.form.value.basicInfos;
    this.userToUpdate.lastName = item.lastName;
    this.userToUpdate.firstName = item.firstName;
    this.userToUpdate.phone = item.phone;
    this.userToUpdate.birthDate = new Date(item.birthDate + '');
    if (this.uploadImageUrl)
      this.userToUpdate.userImagePath = this.uploadImageUrl;
    if (item.phone?.length != 9) {
      this.notificationService.danger("Saisir un numéro de téléphone à 9 chiffres");
    } else if (this.containsAnyLetters(item.phone)) {
      this.notificationService.danger('le numéro de téléphone ne doit pas contenir des lettres');
      this.isLoading = false;
    } else {
      this.userToUpdate.phone = "237" + item.phone;
      this.isLoading = true;
      this.userService.updateAdminOrSpecialistByAdminUsingPOST(this.userToUpdate).toPromise().then(
        res => {
          this.authService.storeUser(this.userToUpdate);
          const item1: Specialist = this.form.value.contactInfos;
          this.specialistToUpdate.city = item1.city;
          this.specialistToUpdate.gender = item1.gender;
          this.specialistToUpdate.yearOfExperience = item1.yearOfExperience;
          this.specialistToUpdate.biography = item1.biography;

          this.specialistService.updateSpecialistUsingPOST(this.specialistToUpdate).toPromise()
            .then(res => {

            })
            .catch(err => {

            })
            .finally(() => {
              this.isLoading = false;
              this.notificationService.success("Modification apporté avec succès");
            });
        }
      ).catch(
        error => {
          if (error.status === 400) {
            if (error?.stringErrorCode === 501 && error?.errorText === EXCEPTION.SPECIALIST_ALREADY_EXIST) {
              this.notificationService.danger(EXCEPTION.SPECIALIST_ALREADY_EXIST);
            } else if (error?.stringErrorCode === 502 && error?.errorText === EXCEPTION.SPECIALIST_ALREADY_DELETED) {
              this.notificationService.danger(EXCEPTION.SPECIALIST_ALREADY_DELETED);
            } else if (error?.stringErrorCode === 501 && error?.errorText === EXCEPTION.SPECIALIST_ALREADY_EXIST) {
              this.notificationService.danger(EXCEPTION.SPECIALIST_ALREADY_EXIST);
            } else if (error?.errorText === EXCEPTION.USERNAME_AND_EMAIL_ALREADY_EXIST) {
              this.notificationService.danger(EXCEPTION.USERNAME_AND_EMAIL_ALREADY_EXIST);
            }
          } else {
            this.notificationService.danger(EXCEPTION.NO_INTERNET_CONNECTION);
          }
          this.isLoading = false;
        }
      )
    }
  }

  public onExpChange(items: any): void {
    this.experiences = items;
  }

  public onAwardChange(items: any): void {
    this.awards = items;
  }

  public onEducChange(items: any): void {
    this.educations = items;
  }

  public deleteItem = (itemToDelete: MultiselecItem) => {
    this.selectedItems = this.selectedItems.filter(item => itemToDelete.id != item.id)
    this.machtList.map(item => {
      if (item.id === itemToDelete.id) {
        item.selected = false;
        return;
      }
    });
    this.specialityId = Number(itemToDelete.id);
    this.specialistId = this.userToUpdate.specialist?.specialistId!;
    this.specialityControllerService.deleteSpecialistSpecialityByIdUsingGET(this.specialistId, this.specialityId).toPromise();
  }

  public selectItem = (itemToAdd: MultiselecItem) => {

    const res = this.selectedItems.filter(item => item.id === itemToAdd.id)

    this.machtList.map(item => {
      if (item.id === itemToAdd.id) {
        item.selected = true;
        this.specialistSpeciality.specialityId = itemToAdd.id;
        this.specialistSpeciality.specialistId = this.userToUpdate.specialist?.specialistId;
        this.specialityControllerService.createSpecialistSpecialityUsingPOST(this.specialistSpeciality).toPromise()
          .then(res => {
            this.userToUpdate.specialist?.specialitiesList?.push(String(res?.speciality?.specialityName));
            this.authService.storeUser(this.userToUpdate);
            this.dispachSpecialityChangeEvent();
            this.notificationService.success("La spécialité" + " " + itemToAdd.title + " " + "a  été ajouté avec succès");
          }).
          catch(error => {
            if (error?.stringErrorCode === 501 && error?.errorText === EXCEPTION.SPECIALIST_ALREADY_EXIST) {
              this.notificationService.danger(EXCEPTION.SPECIALIST_ALREADY_EXIST);
            } else if (error?.stringErrorCode === 502 && error?.errorText === EXCEPTION.SPECIALIST_ALREADY_DELETED) {
              this.notificationService.danger(EXCEPTION.SPECIALIST_ALREADY_DELETED);
            } else if (error?.stringErrorCode === 501 && error?.errorText === EXCEPTION.SPECIALIST_ALREADY_EXIST) {
              this.notificationService.danger(EXCEPTION.SPECIALIST_ALREADY_EXIST);
            } else if (error?.errorText === EXCEPTION.USERNAME_AND_EMAIL_ALREADY_EXIST) {
              this.notificationService.danger(EXCEPTION.USERNAME_AND_EMAIL_ALREADY_EXIST);
            } else {
              this.notificationService.danger(EXCEPTION.NO_INTERNET_CONNECTION)
            }
          }
          );
        return;
      }

    });
    if (res.length === 0) this.selectedItems = [...this.selectedItems, itemToAdd]
  }

  public specialityToDisplay(): void {
    this.specialistId = this.userToUpdate.specialist?.specialistId!;
    this.specialityControllerService.getAllSpecialistSpecialityByIdUsingGET(this.specialistId).toPromise()
      .then(res => {
        res?.forEach(speciality => {
          const multiSelecItem: MultiselecItem = {
            id: speciality.id!,
            title: speciality.specialityName!,
            selected: true
          }
          this.copySelectedItems.push(multiSelecItem);
        })
        this.selectedItems = [];
        this.selectedItems = this.copySelectedItems;
      })
      .catch(err => { })

    return;
  }

  getItemList(term: any): void {
    if (term && term.trim().length > 0) {
      const searchCriteria: SearchCriteriasModel[] = [
        {
          key: 'specialityName',
          operation: '%',
          operatorGroup: 'AND',
          orPredicate: false,
          value: term ? term.trim() : '',
        },
        {
          key: 'status',
          operation: '=',
          operatorGroup: 'AND',
          orPredicate: false,
          value: 0
        }
      ];
      this.searchCriterias = searchCriteria;
      this.specialityControllerService.recordUsingPOST(this.searchCriterias).toPromise().then(res => {
        if (res) {
          this.machtList = [];
          res.content?.forEach((speciality: Speciality) => {
            this.machtList.push({
              id: speciality.id!,
              title: speciality.specialityName!,
              selected: false,
            })
          });
        }

      }).catch(err => {
      })
    } else {
      this.machtList = [];
    }
  }

  public close(): void {
    this.modalService.hide();
    this.modalService;
  }

  public togglePasswordOptions: any = {
    passwordType: 'password',
    confirmPasswordType: 'password'
  }

  public toggleViewPasswordBtn(): void {
    this.togglePasswordOptions.passwordType = this.togglePasswordOptions.passwordType === 'password' ? 'text' : 'password';
  }

  public toggleViewConfirmPasswordBtn(): void {
    this.togglePasswordOptions.confirmPasswordType = this.togglePasswordOptions.confirmPasswordType === 'password' ? 'text' : 'password';
  }

  public containsAnyLetters(str: string): boolean {
    return /[a-zA-Z]/.test(str);
  }

  public dispachSpecialityChangeEvent(): void {
    window.dispatchEvent(new CustomEvent('profile-speciality:change'));
  }
}
