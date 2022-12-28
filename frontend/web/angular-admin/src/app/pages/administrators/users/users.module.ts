import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ButtonsModule } from 'ngx-bootstrap/buttons';

import { UsersRoutingModule } from './users-routing.module';
import { ListUsersComponent } from './list-users/list-users.component';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { CreateUpdateUserModalComponent } from './components/create-update-user-modal/create-update-user-modal.component';
import { RolesConfigurationModalComponent } from './components/roles-configuration-modal/roles-configuration-modal.component';
import { DisplayUsersComponent } from './components/display-users/display-users.component';
import { DataTablesModule } from 'angular-datatables';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    UsersRoutingModule,
    ButtonsModule.forRoot(),
    TooltipModule.forRoot(),
    BsDropdownModule.forRoot(),
    ModalModule.forRoot(),
    DataTablesModule
  ],
  declarations: [
    ListUsersComponent,
    DisplayUsersComponent,
    CreateUpdateUserModalComponent,
    RolesConfigurationModalComponent
  ]
})
export class UsersModule { }
