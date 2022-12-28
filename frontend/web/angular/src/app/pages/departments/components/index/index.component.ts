import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Speciality, SpecialityControllerService } from 'src/app/generated';
import { ImageService } from 'src/app/services/image.service';
import { SectionDepartmentDescComponent } from '../section-department-desc/section-department-desc.component';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.scss']
})
export class IndexComponent implements OnInit {
  public departments: Speciality[] = [];
  public activeDepartment!: Speciality;
  public isLoading: boolean = false;

  constructor(
    public dataService: SpecialityControllerService,
    public imageService: ImageService,
    public route: ActivatedRoute) { }

  public ngOnInit(): void {
    this.getRouteParams();
  }

  public getRouteParams(): void {
    const name: string = this.route.snapshot.paramMap.get('name')!;
    this.getData(name);
  }

  public getData(department?: string): void {
    this.isLoading = true;
    this.dataService.getAllActivatedSpecialitiesUsingGET().toPromise().then(
      res => {
        if (res) {
          this.departments = res;
          if (department && department === 'all') {
            this.activeDepartment = this.departments[0];
          } else {
            this.activeDepartment = this.departments.find(d => (d.specialityName === department))!;
          }
        }
      }
    ).finally(() => {
      this.isLoading = false;
    })
  }
}
