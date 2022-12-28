import { Directive } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';

@Directive({ selector: 'directive-modal-base' })
export class ModalBaseComponent {

  constructor(
    public modalService: BsModalRef) { }

  public close(): boolean {
    this.modalService.hide();
    return false;
  }
}