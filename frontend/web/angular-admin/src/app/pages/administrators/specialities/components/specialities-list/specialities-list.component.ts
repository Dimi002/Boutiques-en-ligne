import { ImageService } from 'src/app/services/image.service';
import { Component, OnDestroy, OnInit, Output } from '@angular/core';
import { Subject } from 'rxjs';
import { Specialist, SpecialistControllerService, Speciality, SpecialityControllerService } from 'src/app/generated';
import { NavigationService } from 'src/app/services/navigation.service';
import { NotificationService } from 'src/app/services/notification.service';
import { EXCEPTION } from 'src/app/utils/constants';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { CreateUpdateSpecialityModalComponent } from '../create-update-speciality-modal/create-update-speciality-modal.component';

@Component({
  selector: 'app-specialities-list',
  templateUrl: './specialities-list.component.html',
  styleUrls: ['./specialities-list.component.scss']
})
export class SpecialitiesListComponent implements OnInit, OnDestroy {
  public specialitiesDtTrigger: Subject<any> = new Subject<any>();
  public items: any[] = [];
  public isLoading: boolean = false;

  constructor(
    public navigationService: NavigationService,
    private specialityService: SpecialityControllerService,
    private specialistService: SpecialistControllerService,
    private notificationService: NotificationService,
    private imageService: ImageService,
    private modalService: BsModalService
  ) { }

  ngOnInit(): void {
    this.loadSpecialities()
  }

  public create = () => {
    const initialState = { mode: 'CREATE' }
    const bsModalRef: BsModalRef = this.modalService.show(CreateUpdateSpecialityModalComponent, { initialState, class: 'modal-primary modal-md' });
    bsModalRef?.onHidden?.subscribe(() => {
      const createdSuccesfully = bsModalRef.content.isSuccess;
      const isError = bsModalRef.content.isError;
      if (createdSuccesfully) {
        this.notificationService.success('Boutique successfully created!');
        //this.getAllAppts();
      }
      if (isError) {
        this.notificationService.danger('Creation failed, an unknown error occurred');
      }
    })
  }

  public update = () => {
    const initialState = { mode: 'UPDATE' }
    const bsModalRef: BsModalRef = this.modalService.show(CreateUpdateSpecialityModalComponent, { initialState, class: 'modal-primary modal-md' });
    bsModalRef?.onHidden?.subscribe(() => {
      const createdSuccesfully = bsModalRef.content.isSuccess;
      const isError = bsModalRef.content.isError;
      if (createdSuccesfully) {
        this.notificationService.success('Boutique successfully created!');
        //this.getAllAppts();
      }
      if (isError) {
        this.notificationService.danger('Creation failed, an unknown error occurred');
      }
    })
  }



  public delete = (itemId: number) => {
    this.isLoading = true;
    this.specialistService.deleteBoutiqueUsingPOST(itemId).toPromise().then(res => {
      this.notificationService.success("La boutique a ete suprime avec succes !")
      this.items = []
      this.loadSpecialities('refresh')
    }).catch(err => {
      if (err.stringErrorCode == 502 && err.errorText == EXCEPTION.SPECIALITY_ALREADY_DELETED) {
        this.notificationService.danger(EXCEPTION.SPECIALITY_ALREADY_DELETED);
      } if (err.stringErrorCode == 403 && err.errorText == EXCEPTION.SPECIALITY_NOT_FOUND) {
        this.notificationService.danger(EXCEPTION.SPECIALITY_NOT_FOUND);
      } else {
        this.notificationService.danger(EXCEPTION.NO_INTERNET_CONNECTION);
      }
    }).finally(() => {
      this.isLoading = false;
    });
  }

  private loadSpecialities(refresh?: any) {
    // this.isLoading = true;
    // this.specialistService.getAllBoutiqueUsingPOST().toPromise().then(res => {
    //   this.items = res
    // }).catch(err => {
    //   this.notificationService.danger(EXCEPTION.NO_INTERNET_CONNECTION);
    // }).finally(() => {
    //   this.isLoading = false;
    //   if (!refresh)
    //     this.specialitiesDtTrigger.next('');
    // })

    // decommente
  }

  public ngOnDestroy(): void {
    this.specialitiesDtTrigger.unsubscribe();
  }

}
