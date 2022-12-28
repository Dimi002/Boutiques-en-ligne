import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { languages } from 'src/app/configs/languages';
import { currenLang } from 'src/app/services/language.service';
import { HomeComponent } from './components/index/home.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    data: {
      title: () => {
        return currenLang === languages.FR? 'Accueil': 'Home' 
      }
      
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule { }
