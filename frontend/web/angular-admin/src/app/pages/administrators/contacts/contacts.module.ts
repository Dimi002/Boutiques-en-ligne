import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ContactsRoutingModule } from './contacts-routing.module';
import { IndexComponent } from './components/index/index.component';
import { ContactsListComponent } from './components/contacts-list/contacts-list.component';
import { DisplayContactsComponent } from './components/display-contacts/display-contacts.component';
import { DataTablesModule } from 'angular-datatables';
import { HttpClient } from '@angular/common/http';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpLoaderFactory } from 'src/app/configs/http-loader-factory';

@NgModule({
  declarations: [
    IndexComponent,
    ContactsListComponent,
    DisplayContactsComponent
  ],
  imports: [
    CommonModule,
    ContactsRoutingModule,
    TranslateModule.forChild({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    DataTablesModule
  ],
  exports: [ DisplayContactsComponent ]
})
export class ContactsModule { }
