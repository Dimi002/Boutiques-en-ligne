import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-section-secondary-title',
  templateUrl: './section-secondary-title.component.html',
  styleUrls: ['./section-secondary-title.component.scss']
})
export class SectionSecondaryTitleComponent {
  @Input() data?: { title: string, description: string };

  constructor() { }
}
