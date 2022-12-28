import { Component, OnInit } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';

/**
 * @author Arléon Zemtsop
 * @email arleonzemtsop@gmail.com
 */
@Component({
  selector: 'app-confirmation-modal',
  templateUrl: './confirmation-modal.component.html',
  styleUrls: ['./confirmation-modal.component.scss']
})
export class ConfirmationModalComponent implements OnInit {
  public data?: { title: string, message: string };
  public agree: boolean = false;

  constructor(
    public modalService: BsModalRef
  ) { }

  ngOnInit(): void {
  }

  public close(): void {
    this.modalService.hide();
  }

  public delete(): void {
    this.agree = true;
    this.close();
  }
}
