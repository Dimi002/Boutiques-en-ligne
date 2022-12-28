import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { Contact, ContactControllerService, SpecialistControllerService } from 'src/app/generated';
import { NotificationService } from 'src/app/services/notification.service';
import { EXCEPTION } from 'src/app/utils/constants';

@Component({
  selector: 'app-contacts-list',
  templateUrl: './contacts-list.component.html',
  styleUrls: ['./contacts-list.component.scss']
})
export class ContactsListComponent implements OnInit {

  public contactsDtTrigger: Subject<any> = new Subject<any>();
  public contacts: Contact[] = [];

  public isLoading: boolean = false;

  constructor(
    public contactService: ContactControllerService,
    public notifService: NotificationService
  ) { }

  ngOnInit(): void {
    this.getAllContacts();
  }

  getAllContacts(): void {
    this.isLoading = true;
    this.contactService.recordsUsingGET().toPromise().then(res => {
      this.contacts = res!;
    }).catch(error => {
      if (error && error?.errorText == EXCEPTION.CONTACT_IS_NULL)
        this.notifService.success(EXCEPTION.CONTACT_IS_NULL);
    }).finally(() => {
      this.isLoading = false;
      this.contactsDtTrigger.next('');
    });
  }

  public ngOnDestroy(): void {
    this.contactsDtTrigger.unsubscribe();
  }

}
