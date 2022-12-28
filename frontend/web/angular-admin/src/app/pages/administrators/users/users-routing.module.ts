import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ROLES } from '../../../utils/constants';
import { CreateUpdateUserModalComponent } from './components/create-update-user-modal/create-update-user-modal.component';


import { ListUsersComponent } from './list-users/list-users.component';

const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Users',
      roles: [ROLES.ADMIN_ROLE, ROLES.SUPER_ADMIN_ROLE]
    },
    children: [
      {
        path: '',
        component: ListUsersComponent,
        data: {
          title: 'Users list'
        }
      },
      {
        path: 'update/:id',
        component: CreateUpdateUserModalComponent,
        data: {
          title: "Update User"
        }
      },
      {
        path: 'create',
        component: CreateUpdateUserModalComponent,
        data: {
          title: "New User"
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsersRoutingModule { }
