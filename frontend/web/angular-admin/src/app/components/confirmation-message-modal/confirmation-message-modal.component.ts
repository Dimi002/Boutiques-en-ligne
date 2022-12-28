import { Component, OnInit } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';

/**
 * @author Arléon Zemtsop
 * @email arleonzemtsop@gmail.com
 */
@Component({
  selector: 'app-confirmation-message-modal',
  templateUrl: './confirmation-message-modal.component.html',
  styleUrls: ['./confirmation-message-modal.component.scss']
})
export class ConfirmationMessageModalComponent implements OnInit {
  public icon?: string;
  public message?: string;

  constructor(
    public modalService: BsModalRef
  ) { }

  ngOnInit(): void { }

  public close(): void {
    this.modalService.hide();

  }
}
