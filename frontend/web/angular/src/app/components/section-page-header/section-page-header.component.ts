import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-section-page-header',
  templateUrl: './section-page-header.component.html',
  styleUrls: ['./section-page-header.component.scss']
})
export class SectionPageHeaderComponent {
  @Input() data?: { pageTitle: string, icon: string };

  constructor() { }
}
