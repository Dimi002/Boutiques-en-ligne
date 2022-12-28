import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-logo-mini',
  templateUrl: './logo-mini.component.html',
  styleUrls: ['./logo-mini.component.scss']
})
export class LogoMiniComponent implements OnInit {
  @Input() customClass: string = '';

  constructor() { }

  ngOnInit(): void {
  }

}
