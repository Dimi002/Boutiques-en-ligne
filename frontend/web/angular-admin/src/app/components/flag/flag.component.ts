import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-flag',
  templateUrl: './flag.component.html',
  styleUrls: ['./flag.component.scss']
})
export class FlagComponent implements OnInit {
  @Input() name!: string;

  constructor() { }

  ngOnInit(): void {}

}
