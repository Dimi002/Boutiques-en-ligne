import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Editor, Toolbar, Validators as ReachTextValidators } from 'ngx-editor';
import { DisplayImageModalComponent } from 'src/app/components/display-image-modal/display-image-modal.component';
import { Boutique, Specialist, SpecialistControllerService, User, UserControllerService } from 'src/app/generated';
import { SpecialityControllerService } from 'src/app/generated/api/specialityController.service';
import { Speciality } from 'src/app/generated/model/speciality';
import { UploadResponse } from 'src/app/generated/model/uploadResponse';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ImageService } from 'src/app/services/image.service';
import { NavigationService } from 'src/app/services/navigation.service';
import { NotificationService } from 'src/app/services/notification.service';
import { EXCEPTION, MODAL_MODE } from 'src/app/utils/constants';

/**
 * @author Maestros
 * @email roslyn.temateu@dimsoft.eu
 */
@Component({
  selector: 'app-create-update-speciality-modal',
  templateUrl: './create-update-speciality-modal.component.html',
  styleUrls: ['./create-update-speciality-modal.component.scss']
})
export class CreateUpdateSpecialityModalComponent implements OnInit, OnDestroy {

  public mode: string = '';

  public specialityToCreate: Speciality = {};
  public specialityToUpdate: Speciality = {};

  public spect: Specialist = {}

  public file: any;

  public specialistImage: { file: File | null, url: string } = {
    file: null,
    url: ''
  };
  public modelImage: any = null;
  public imagePath: string = '';
  public id: any;

  public isLoading: boolean = false;
  public isError: boolean = false;
  public isSuccess: boolean = false;
  public admin: boolean = false;
  public specialist: boolean = false;

  private reachTextResponse: string = '';
  public reachTextBinded: string = '';


  //reach text properties

  public placeholder: string = 'Enter your speciality description';
  public editor: Editor = new Editor();
  public toolbar: Toolbar = [
    ['bold', 'italic'],
    ['underline', 'strike'],
    ['code', 'blockquote'],
    ['ordered_list', 'bullet_list'],
    [{ heading: ['h1', 'h2', 'h3', 'h4', 'h5', 'h6'] }],
    ['link', 'image'],
    ['text_color', 'background_color'],
    ['align_left', 'align_center', 'align_right', 'align_justify'],
  ];
  public colorPresets: string[] = ['aliceblue', 'antiquewhite', 'aqua', 'aquamarine', 'azure', 'beige', 'bisque', 'black', 'blanchedalmond', 'blue', 'blueviolet', 'brown', 'burlywood', 'cadetblue', 'chartreuse', 'chocolate', 'coral', 'cornflowerblue', 'cornsilk', 'crimson', 'cyan', 'darkblue', 'darkcyan', 'darkgoldenrod', 'darkgray', 'darkgrey', 'darkgreen', 'darkkhaki', 'darkmagenta', 'darkolivegreen', 'darkorange', 'darkorchid', 'darkred', 'darksalmon', 'darkseagreen', 'darkslateblue', 'darkslategray', 'darkslategrey', 'darkturquoise', 'darkviolet', 'deeppink', 'deepskyblue', 'dimgray', 'dimgrey', 'dodgerblue', 'firebrick', 'floralwhite', 'forestgreen', 'fuchsia', 'gainsboro', 'ghostwhite', 'gold', 'goldenrod', 'gray', 'grey', 'green', 'greenyellow', 'honeydew', 'hotpink', 'indianred', 'indigo', 'ivory', 'khaki', 'lavender', 'lavenderblush', 'lawngreen', 'lemonchiffon', 'lightblue', 'lightcoral', 'lightcyan', 'lightgoldenrodyellow', 'lightgray', 'lightgrey', 'lightgreen', 'lightpink', 'lightsalmon', 'lightseagreen', 'lightskyblue', 'lightslategray', 'lightslategrey', 'lightsteelblue', 'lightyellow', 'lime', 'limegreen', 'linen', 'magenta', 'maroon', 'mediumaquamarine', 'mediumblue', 'mediumorchid', 'mediumpurple', 'mediumseagreen', 'mediumslateblue', 'mediumspringgreen', 'mediumturquoise', 'mediumvioletred', 'midnightblue', 'mintcream', 'mistyrose', 'moccasin', 'navajowhite', 'navy', 'oldlace', 'olive', 'olivedrab', 'orange', 'orangered', 'orchid', 'palegoldenrod', 'palegreen', 'paleturquoise', 'palevioletred', 'papayawhip', 'peachpuff', 'peru', 'pink', 'plum', 'powderblue', 'purple', 'red', 'rosybrown', 'royalblue', 'saddlebrown', 'salmon', 'sandybrown', 'seagreen', 'seashell', 'sienna', 'silver', 'skyblue', 'slateblue', 'slategray', 'slategrey', 'snow', 'springgreen', 'steelblue', 'tan', 'teal', 'thistle', 'tomato', 'turquoise', 'violet', 'wheat', 'white', 'whitesmoke', 'yellow', 'yellowgreen'];

  public form: FormGroup = this.fb.group({});

  public currentUser: User = this.authService.getUser();

  public items: User[] = []


  constructor(
    public modalService: BsModalService,
    private fb: FormBuilder,
    public specialityService: SpecialityControllerService,
    public specialistService: SpecialistControllerService,
    public imageUploadService: ImageService,
    public notificationService: NotificationService,
    public authService: AuthenticationService,
    public navigationService: NavigationService,
    private activatedroute: ActivatedRoute,
    private userService: UserControllerService
  ) { }

  ngOnInit(): void {
    this._init();
    this.initForm();
    this.getAllUser()
  }


  ngOnDestroy(): void {
    this.editor.destroy();
  }

  public _init(): void {

    this.activatedroute.paramMap.subscribe(params => {
      this.id = params.get('id');
    });
    if (this.id == 0 || !this.id)
      this.mode = MODAL_MODE.create;
    else
      this.mode = MODAL_MODE.update;
  }

  get f() {
    return this.form.controls;
  }


  reachTextContentMessage = (reachTextContent: any) => {
    this.reachTextResponse = reachTextContent;
  }


  public initForm(): void {
    this.form = this.fb.group({
      boutiqueName: ['', Validators.required],
      boutiqueQuater: ['', Validators.required],
      boutiqueImage: ['', Validators.required],
      boutiqueUser: ['', Validators.required],
      boutiqueDescription: [''],
      // specialityImagePath: this.mode === MODAL_MODE.update ? [''] : ['', Validators.required],
    });
    if (this.mode === MODAL_MODE.update && this.id) {
      this.specialityService.findSpecialityByIdUsingGET(this.id).toPromise().then((speciality?: Speciality) => {
        if (speciality) {
          this.specialityToUpdate = speciality;
          this.form.patchValue({
            id: this.specialityToUpdate.id,
            specialityName: this.specialityToUpdate.specialityName,
            specialistCommonName: this.specialityToUpdate.specialistCommonName,
            specialityDesc: this.specialityToUpdate.specialityDesc,
            longDescription: this.specialityToUpdate.longDescription,
          });
          this.reachTextBinded = this.specialityToUpdate.longDescription!;
          if (this.specialityToUpdate.specialityImagePath) {
            this.imagePath = this.imageUploadService.getCover(this.specialityToUpdate.specialityImagePath);
          }

        }
      }).catch(error => {
        this.notificationService.danger("The speciality was not found !");
      });
    }
  }

  public saveSpeciality(): void {
    const specialist: any = this.form.value;
    const ss: Boutique = {
      boutiqueDescription: specialist.boutiqueDescription,
      boutiqueImage: this.file,
      boutiqueName: specialist.boutiqueName,
      boutiqueQuater: specialist.boutiqueQuater,
      boutiqueUser: specialist.boutiqueUser
    }
    this.specialistService.createBoutiqueUsingPOST(specialist).toPromise().then(
      res => {
        if (res) {
          this.isLoading = false;
          this.form.reset();
          this.notificationService.success("Création éffectuée avec succès");
        }
      }
    ).catch(
      error => {
        if (error?.stringErrorCode == 501 && error?.errorText == EXCEPTION.SPECIALITY_NAME_ALREADY_EXIST) {
          this.notificationService.danger(EXCEPTION.SPECIALITY_NAME_ALREADY_EXIST);
        } else if (error?.stringErrorCode == 502 && error?.errorText == EXCEPTION.SPECIALITY_ALREADY_DELETED) {
          this.notificationService.danger(EXCEPTION.SPECIALITY_ALREADY_DELETED);
        } else {
          this.notificationService.danger(EXCEPTION.NO_INTERNET_CONNECTION);
        }
        this.isLoading = false;
      }
    ).finally(
      () => {
        this.isLoading = false;
      }
    )
  }

  public getAllUser(refresh?: any) {
    this.isLoading = true;
    this.userService.getAllUserUsingGET().toPromise().then(res => {
      this.items = res
    }).catch(err => {
      this.notificationService.danger(EXCEPTION.NO_INTERNET_CONNECTION);
    }).finally(() => {
      this.isLoading = false;
    })

  }

  public showImage = () => {
    if (this.specialistImage.file) {
      this.imageUploadService.toBase64(this.specialistImage.file)
        .then(img => {

          const initialState = { image: img };
          const modalRef: BsModalRef<DisplayImageModalComponent> = this.modalService.show(DisplayImageModalComponent, {
            initialState, class: "modal-secondary modal-xl", ignoreBackdropClick: false
          });
          modalRef.onHide?.subscribe(() => {
            const res = modalRef.content;

          })
        }).catch(err => {

        })
    }

  }


  // public save(): void {
  //   let item: Speciality = this.form.value;
  //   item.longDescription = this.specialityToCreate.longDescription = this.reachTextResponse;
  //   this.specialityToCreate.specialityName = item.specialityName;
  //   this.specialityToCreate.specialistCommonName = item.specialistCommonName;
  //   this.specialityToCreate.specialityDesc = item.specialityDesc;
  //   if (this.mode === MODAL_MODE.update) item.id = this.specialityToUpdate.id;


  //   if (item.specialityDesc?.length! > 500) {
  //     this.notificationService.danger("This description is more than 500 characters")
  //   } else if (!this.modelImage && this.mode === MODAL_MODE.create) {
  //     this.notificationService.danger("Please provide an image to this speciality")
  //   } else {
  //     this.isLoading = true;
  //     if (this.specialistImage.file) {
  //       this.imageUploadService.upload([this.specialistImage.file]).toPromise()
  //         .then((res?: UploadResponse[]) => {
  //           if (res) {
  //             item = { ...item, specialityImagePath: res[0].uploadLocation };

  //             if (this.mode === MODAL_MODE.create) { this.saveSpeciality(item); }
  //             else { this.updateSpeciality(item); }
  //           }

  //         }).catch(err => {
  //           const message = this.mode === MODAL_MODE.create ? 'Unable to create this speciality' : 'Unable to update this speciality';
  //           this.notificationService.danger(message);
  //         }).finally(
  //           () => {
  //             this.isLoading = false;
  //           }
  //         )
  //     } else {
  //       if (this.mode === MODAL_MODE.create) { this.saveSpeciality(item); }
  //       else {
  //         item.specialityImagePath = this.specialityToUpdate.specialityImagePath
  //         this.updateSpeciality(item);
  //       }
  //     }
  //   }
  // }

  public updateSpeciality = (item: Speciality): void => {
    this.specialityService.updateSpecialityUsingPOST(item).toPromise().then(
      res => {
        if (res) {
          this.isLoading = false;
          this.form.reset();
          this.navigationService.goTo('/home/admin-specialities/');
          this.notificationService.success('This speciality has been successfully updated!');
        }
      }
    ).catch(
      error => {
        if (error?.stringErrorCode == 500 && error?.errorText == "This speciality don't exists") {
          this.notificationService.danger("Cette spécialité n'existe pas, veuillez vérifier le nom saisi");
        } else if (error?.stringErrorCode == 501 && error?.errorText == EXCEPTION.SPECIALITY_NAME_ALREADY_EXIST) {
          this.notificationService.danger(EXCEPTION.SPECIALITY_NAME_ALREADY_EXIST);
        } else if (error?.stringErrorCode == 502 && error?.errorText == EXCEPTION.SPECIALITY_ALREADY_DELETED) {
          this.notificationService.danger(EXCEPTION.SPECIALITY_ALREADY_DELETED);
        }
        else {
          this.notificationService.danger(EXCEPTION.NO_INTERNET_CONNECTION);
        }
        this.isLoading = false;
      }
    ).finally(
      () => {
        this.isLoading = false;
      }
    )
  }

  public selectFile(event: any): void {
    this.file = event.target.files[0];
  }

  public validateFile(file: File): boolean {
    const pattern: string[] = ['image/png', 'image/jpg', 'image/jpeg'];
    return pattern.includes(file.type);
  }
  public create() {

  }
  public update() {

  }
  public close(): void {
    this.modalService.hide();
  }

}
