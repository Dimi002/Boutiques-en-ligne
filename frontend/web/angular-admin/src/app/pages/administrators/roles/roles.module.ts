import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ButtonsModule } from 'ngx-bootstrap/buttons';

import { RolesRoutingModule } from './roles-routing.module';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { ListRolesComponent } from './list-roles/list-roles.component';
import { CreateUpdateRoleModalComponent } from './components/create-update-role-modal/create-update-role-modal.component';
import { PermissionsConfigurationModalComponent } from './components/permissions-configuration-modal/permissions-configuration-modal.component';
import { DataTablesModule } from 'angular-datatables';
import { DisplayRolesComponent } from './components/display-roles/display-roles.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RolesRoutingModule,
    ButtonsModule.forRoot(),
    TooltipModule.forRoot(),
    BsDropdownModule.forRoot(),
    ModalModule.forRoot(),
    DataTablesModule
  ],
  declarations: [
    ListRolesComponent,
    DisplayRolesComponent,
    CreateUpdateRoleModalComponent,
    PermissionsConfigurationModalComponent
  ]
})
export class RolesModule { }
