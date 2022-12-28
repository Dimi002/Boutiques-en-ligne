import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-testimonial-widget',
  templateUrl: './testimonial-widget.component.html',
  styleUrls: ['./testimonial-widget.component.scss']
})
export class TestimonialWidgetComponent {
  @Input() testimonial!: { name: string, message: string };

  constructor() { }

}
