import { Component, Input } from '@angular/core';
import { Speciality } from 'src/app/generated';
import { ImageService } from 'src/app/services/image.service';

@Component({
  selector: 'app-section-department-desc',
  templateUrl: './section-department-desc.component.html',
  styleUrls: ['./section-department-desc.component.scss']
})
export class SectionDepartmentDescComponent {
  @Input() activeDepartment?: Speciality;
  public animationClass: string = 'animated fadeIn';

  constructor(
    public imageService: ImageService) { }

}
