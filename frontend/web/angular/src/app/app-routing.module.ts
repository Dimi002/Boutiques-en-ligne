import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { languages } from './configs/languages';
import { DefaultLayoutComponent } from './containers';
import { currenLang } from './services/language.service';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: '',
    component: DefaultLayoutComponent,
    data: {
      title: () => {
        return currenLang === languages.FR? 'Accueil': 'Home' 
      }
    },
    children: [
      {
        path: 'home',
        loadChildren: () =>
          import('./pages/home/home.module').then((m) => m.HomeModule)
      },
      {
        path: 'take-appointment',
        loadChildren: () =>
          import('./pages/appointment/appointment.module').then((m) => m.AppointmentModule)
      },
      {
        path: 'take-appointment/:specialistId',
        loadChildren: () =>
          import('./pages/appointment/appointment.module').then((m) => m.AppointmentModule)
      },
      {
        path: 'gallery',
        loadChildren: () =>
          import('./pages/gallery/galerie.module').then((m) => m.GalerieModule)
      },
      {
        path: 'departments/:name',
        loadChildren: () =>
          import('./pages/departments/departments.module').then((m) => m.DepartmentsModule)
      },
      {
        path: 'about-us',
        loadChildren: () =>
          import('./pages/about-us/about-us.module').then((m) => m.AboutUsModule)
      },
      {
        path: 'contact-us',
        loadChildren: () =>
          import('./pages/contact-us/contact-us.module').then((m) => m.ContactUsModule)
      }
    ]
  },
  { path: '**', redirectTo: 'dashboard' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
