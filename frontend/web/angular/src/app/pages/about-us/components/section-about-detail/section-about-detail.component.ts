import { Component, Input, OnInit } from '@angular/core';
import * as AOS from 'aos';

@Component({
  selector: 'app-section-about-detail',
  templateUrl: './section-about-detail.component.html',
  styleUrls: ['./section-about-detail.component.scss']
})
export class SectionAboutDetailComponent implements OnInit {
  @Input() aboutUsDetail!: { description: string, img: string };
  @Input() index!: number;

  constructor() { }

  ngOnInit(): void {
    AOS.init({disable: 'mobile'});
    AOS.refresh();
  }

}
