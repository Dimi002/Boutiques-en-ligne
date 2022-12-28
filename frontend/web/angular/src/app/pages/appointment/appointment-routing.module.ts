import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PageBannerBreadcrumbComponent } from 'src/app/components/page-banner-breadcrumb/page-banner-breadcrumb.component';
import { languages } from 'src/app/configs/languages';
import { currenLang } from 'src/app/services/language.service';
import { AppointmentBookingSuccessComponent } from './components/appointment-booking-success/appointment-booking-success.component';
import { AppointmentBookingVerifyComponent } from './components/appointment-booking-verify/appointment-booking-verify.component';
import { IndexComponent } from './components/index/index.component';

const routes: Routes = [
  {
    path: '',
    component: PageBannerBreadcrumbComponent,
    children: [
      {
        path: '',
        component: IndexComponent,
        data: {
          title: () => {
            return currenLang === languages.FR? 'Rendez-Vous': 'Appointments' 
          },
          cover: '/assets/website/img/doctors/doctor-05.jpg'
        }
      },
      {
        path: 'appointment-booking-success',
        component: AppointmentBookingSuccessComponent,
        data: {
          title: () => {
            return currenLang === languages.FR? 'Rendez-Vous Enregistré': 'Booking Success' 
          },
          cover: '/assets/website/img/img-01.png'
        }
      },
      {
        path: 'appointment-booking-verify',
        component: AppointmentBookingVerifyComponent,
        data: {
          title: () => {
            return currenLang === languages.FR? 'Confirmation de Rendez-Vous': 'Booking Verify' 
          },
          cover: '/assets/website/img/img-01.png'
        }
      }
    ] 
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AppointmentRoutingModule { }
