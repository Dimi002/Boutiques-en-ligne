import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ButtonsModule } from 'ngx-bootstrap/buttons';

import { PermissionsRoutingModule } from './permissions-routing.module';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { ListPermissionsComponent } from './list-permissions/list-permissions.component';
import { DisplayPermissionsComponent } from './components/display-permissions/display-permissions.component';
import { CreateUpdatePermissionModalComponent } from './components/create-update-permission-modal/create-update-permission-modal.component';
import { DataTablesModule } from 'angular-datatables';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    PermissionsRoutingModule,
    ButtonsModule.forRoot(),
    TooltipModule.forRoot(),
    BsDropdownModule.forRoot(),
    ModalModule.forRoot(),
    DataTablesModule
  ],
  declarations: [
    ListPermissionsComponent,
    DisplayPermissionsComponent,
    CreateUpdatePermissionModalComponent
  ]
})
export class PermissionsModule { }
