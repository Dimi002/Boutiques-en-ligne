import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Speciality } from 'src/app/generated';

@Component({
  selector: 'app-section-specialities-header',
  templateUrl: './section-specialities-header.component.html',
  styleUrls: ['./section-specialities-header.component.scss']
})
export class SectionSpecialitiesHeaderComponent {
  @Output() specialitySelected: EventEmitter<Speciality> = new EventEmitter<Speciality>();
  @Input() specialities: any[] = [];
  
  constructor() { }

  public onSelectSpeciality(selectedSpeciality: any): void {
    selectedSpeciality.selected = true;
    this.specialitySelected.emit(selectedSpeciality);
    this.specialities.forEach(speciality => {
      if(speciality !== selectedSpeciality ) {
        speciality.selected = false;
      }
    });
  }

}
