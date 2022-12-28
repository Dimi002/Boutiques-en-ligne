import { ImageService } from 'src/app/services/image.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { Speciality, SpecialityControllerService } from 'src/app/generated';
import { NavigationService } from 'src/app/services/navigation.service';
import { NotificationService } from 'src/app/services/notification.service';
import { EXCEPTION } from 'src/app/utils/constants';

@Component({
  selector: 'app-specialities-list',
  templateUrl: './specialities-list.component.html',
  styleUrls: ['./specialities-list.component.scss']
})
export class SpecialitiesListComponent implements OnInit, OnDestroy {
  public specialitiesDtTrigger: Subject<any> = new Subject<any>();
  public items: Speciality[] = [];
  public isLoading: boolean = false;

  constructor(
    public navigationService: NavigationService,
    private specialityService: SpecialityControllerService,
    private notificationService: NotificationService,
    private imageService: ImageService,
  ) { }

  ngOnInit(): void {
    this.loadSpecialities()
  }

  public create = () => {
    this.navigationService.goTo('/home/admin-specialities/newSpeciality/0');
  }

  public delete = (itemId: number) => {
    this.isLoading = true;
    this.specialityService.deleteSpecialityUsingGET(itemId).toPromise().then(res => {
      this.notificationService.success("This speciality was deleted succefully !")
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
    this.isLoading = true;
    this.specialityService.getAllActivatedSpecialitiesUsingGET().toPromise()
      .then((specialities?: Speciality[]) => {
        this.items = specialities!;
      }).finally(() => {
        this.isLoading = false;
        if (!refresh)
          this.specialitiesDtTrigger.next('');
      })
  }

  public ngOnDestroy(): void {
    this.specialitiesDtTrigger.unsubscribe();
  }

}
