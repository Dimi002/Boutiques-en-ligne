import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateUpdateSpecialityModalComponent } from './components/create-update-speciality-modal/create-update-speciality-modal.component';
import { IndexComponent } from './components/index/index.component';

const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Specialities',
    },
    children: [
      {
        path: '',
        component: IndexComponent,
        data: {
          title: 'Speciality list'
        }
      },
      {
        path: 'newSpeciality/:id',
        component: CreateUpdateSpecialityModalComponent,
        data: {
          title: 'New speciality'
        }
      },
      {
        path: 'editSpeciality/:id',
        component: CreateUpdateSpecialityModalComponent,
        data: {
          title: 'Edit speciality'
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SpecialitiesRoutingModule { }
