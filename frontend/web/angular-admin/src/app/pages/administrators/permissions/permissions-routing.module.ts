import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ROLES } from '../../../utils/constants';
import { AdminGuard } from '../guards/admin.guard';
import { ListPermissionsComponent } from './list-permissions/list-permissions.component';

const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Permissions',
      roles: [ROLES.ADMIN_ROLE, ROLES.SUPER_ADMIN_ROLE]
    },
    canActivate: [AdminGuard],
    children: [
      {
        path: '',
        component: ListPermissionsComponent,
        data: {
          title: 'Permissions List'
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PermissionsRoutingModule {}
