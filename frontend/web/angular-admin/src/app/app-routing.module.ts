import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminDefaultLayoutComponent, DoctorDefaultLayoutComponent } from './containers';
import { AdminGuard } from './pages/administrators/guards/admin.guard';
import { SpecialistGuard } from './pages/administrators/guards/specialist.guard';
import { Page404Component } from './pages/error/page404/page404.component';
import { AuthGuard } from './utils/auth.guard';
import { LoginGuard } from './utils/login.guard';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  {
    path: 'home',
    redirectTo: 'home/boutique-dashboard',
    pathMatch: 'full'
  },
  {
    path: 'login',
    loadChildren: () => import('./pages/shared/login/login.module').then(m => m.LoginModule),
    canActivate: [LoginGuard]
  },
  {
    path: 'home',
    component: DoctorDefaultLayoutComponent,
    data: {
      title: 'Home'
    },
    children: [
      {
        path: 'boutique-dashboard',
        loadChildren: () =>
          import('./pages/doctors/doctor-dashboard/doctor.module').then((m) => m.DoctorModule),
        canActivate: [AuthGuard, SpecialistGuard]
      },
      {
        path: 'boutique-profile-settings',
        loadChildren: () =>
          import('./pages/doctors/profile-settings/profile-settings.module').then((m) => m.ProfileSettingsModule),
        canActivate: [AuthGuard, SpecialistGuard]
      },
      {
        path: 'boutique-social-media',
        loadChildren: () =>
          import('./pages/doctors/social-media/social-media.module').then((m) => m.SocialMediaModule),
        canActivate: [AuthGuard, SpecialistGuard]
      },
      {
        path: 'boutique-change-password',
        loadChildren: () =>
          import('./pages/doctors/change-password/change-password.module').then((m) => m.ChangePasswordModule),
        canActivate: [AuthGuard, SpecialistGuard]
      }
    ]
  },
  {
    path: 'home',
    component: AdminDefaultLayoutComponent,
    data: {
      title: 'Dashboard'
    },
    children: [
      {
        path: 'admin-dashboard',
        loadChildren: () =>
          import('./pages/administrators/admin-dashbord/admin.module').then((m) => m.AdminModule),
        canActivate: [AuthGuard, AdminGuard]
      },
      {
        path: 'admin-produits',
        loadChildren: () =>
          import('./pages/administrators/doctors/doctors.module').then((m) => m.DoctorsModule),
        canActivate: [AuthGuard, AdminGuard]
      },
      {
        path: 'admin-contacts',
        loadChildren: () =>
          import('./pages/administrators/contacts/contacts.module').then((m) => m.ContactsModule),
        canActivate: [AuthGuard, AdminGuard]
      },
      {
        path: 'admin-boutique',
        loadChildren: () =>
          import('./pages/administrators/specialities/specialities.module').then((m) => m.SpecialitiesModule),
        canActivate: [AuthGuard, AdminGuard]
      },
      {
        path: 'admin-users',
        loadChildren: () =>
          import('./pages/administrators/users/users.module').then((m) => m.UsersModule),
        canActivate: [AuthGuard, AdminGuard]
      },
      {
        path: 'admin-roles',
        loadChildren: () =>
          import('./pages/administrators/roles/roles.module').then((m) => m.RolesModule),
        canActivate: [AuthGuard, AdminGuard]
      },
      {
        path: 'admin-permissions',
        loadChildren: () =>
          import('./pages/administrators/permissions/permissions.module').then((m) => m.PermissionsModule),
        canActivate: [AuthGuard, AdminGuard]
      },
      {
        path: 'admin-settings',
        loadChildren: () =>
          import('./pages/administrators/settings/settings.module').then((m) => m.SettingsModule),
        canActivate: [AuthGuard, AdminGuard]
      }
    ]
  },
  {
    path: '404',
    component: Page404Component
  },
  { path: '**', redirectTo: '404' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
