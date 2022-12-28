import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PageBannerBreadcrumbComponent } from 'src/app/components/page-banner-breadcrumb/page-banner-breadcrumb.component';
import { languages } from 'src/app/configs/languages';
import { currenLang } from 'src/app/services/language.service';
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
            return currenLang === languages.FR? 'Nos Départements': 'Our Departments' 
          }
        }
      }
    ] 
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DepartmentsRoutingModule { }
