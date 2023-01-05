import { Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { ConfirmationModalComponent } from 'src/app/components/confirmation-modal/confirmation-modal.component';
import { ImageService } from 'src/app/services/image.service';
import { NavigationService } from 'src/app/services/navigation.service';
import { NotificationService } from 'src/app/services/notification.service';
import { CreateUpdateSpecialityModalComponent } from '../create-update-speciality-modal/create-update-speciality-modal.component';

@Component({
  selector: 'app-display-specialities',
  templateUrl: './display-specialities.component.html',
  styleUrls: ['./display-specialities.component.scss']
})
export class DisplaySpecialitiesComponent implements OnInit, OnDestroy {
  public dtOptions: DataTables.Settings = {};
  @Input() dtTrigger?: any;
  @Input() items: any[] = [];
  @Output() deleteEvent: EventEmitter<number> = new EventEmitter<number>();
  @Input() isLoading: boolean = false;

  constructor(
    private modalService: BsModalService,
    private navigationService: NavigationService,
    public imageService: ImageService,
    private notificationService: NotificationService,
  ) { }

  ngOnInit(): void {
    this.getData();
  }

  public getData(): void {
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 5
    };
  }


  public detail(item: any): void {
  }

  public delete(item: any): void {
    const initialState = { data: { title: 'Delete Boutique', message: `Are you sure ? the ${item.boutiqueName} item will be deleted` } };
    const bsModalRef = this.modalService.show(ConfirmationModalComponent, { initialState, class: 'modal-danger modal-sm' });
    bsModalRef?.onHide?.subscribe(() => {
      const agree = bsModalRef?.content?.agree;
      if (agree) {
        if (agree && agree === true) {
          this.deleteEvent.emit(item.specialistId);
        }
      }
    })
  }

  public update = (item: any) => {
    const initialState = { mode: 'UPDATE' }
    const bsModalRef: BsModalRef = this.modalService.show(CreateUpdateSpecialityModalComponent, { initialState, class: 'modal-primary modal-md' });
    bsModalRef?.onHidden?.subscribe(() => {
      const createdSuccesfully = bsModalRef.content.isSuccess;
      const isError = bsModalRef.content.isError;
      if (createdSuccesfully) {
        this.notificationService.success('Boutique successfully Update!');
        //this.getAllAppts();
      }
      if (isError) {
        this.notificationService.danger('Creation failed, an unknown error occurred');
      }
    })
  }

  public cancel(item: any): void {
  }

  public edit(item: any): void {
    this.navigationService.goTo('/home/admin-specialities/editSpeciality/' + item.id);
  }

  public ngOnDestroy(): void {
    this.dtTrigger.unsubscribe();
  }
}
