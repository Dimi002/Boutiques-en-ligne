import { Component, OnInit } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-display-image-modal',
  templateUrl: './display-image-modal.component.html',
  styleUrls: ['./display-image-modal.component.scss']
})
export class DisplayImageModalComponent implements OnInit {

  public image: any;


  ngOnInit(): void {}

  constructor(
    public modalService: BsModalRef
  ) { }

  public close(): void {
    this.modalService.hide();

  }

}
