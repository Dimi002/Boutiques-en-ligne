import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SettingsFormComponent } from './components/settings-form/settings-form.component';

const routes: Routes = [
  {
    path: '',
    component: SettingsFormComponent,
    data: {
      title: 'Settings'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SettingsRoutingModule { }
