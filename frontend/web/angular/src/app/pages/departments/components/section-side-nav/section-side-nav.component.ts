import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Speciality } from 'src/app/generated';

@Component({
  selector: 'app-section-side-nav',
  templateUrl: './section-side-nav.component.html',
  styleUrls: ['./section-side-nav.component.scss']
})
export class SectionSideNavComponent {
  @Input() departments: Speciality[] = [];
  @Input() activeDepartment!: Speciality;
  @Output() departmentChange: EventEmitter<Speciality> = new EventEmitter<Speciality>;

  constructor() { }

  public sectActiveDepartment(department: Speciality): void {
    this.departmentChange.emit(department);
  }

}
