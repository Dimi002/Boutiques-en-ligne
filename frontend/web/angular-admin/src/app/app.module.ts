import { HashLocationStrategy, LocationStrategy, PathLocationStrategy } from '@angular/common';
import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { HttpClient, HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { TokenInterceptor } from './utils/token.interceptor';
import { ErrorInterceptor } from './utils/error.interceptor';

//provider
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';


// Import containers
import { ToastrModule } from 'ngx-toastr';
import { AdminDefaultFooterComponent, AdminDefaultHeaderComponent, AdminDefaultLayoutComponent, DoctorDefaultFooterComponent, DoctorDefaultHeaderComponent, DoctorDefaultLayoutComponent } from './containers';
import { SharedComponentsModule } from './components/shared-components.module';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { AdminDefaultSidebarComponent } from './containers/admin-default-layout/admin-default-sidebar/admin-default-sidebar.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { DoctorDefaultHeaderBaseComponent } from './containers/doctor-default-layout/doctor-default-header/doctor-default-header-base.component';
import { DoctorDefaultSidebarComponent } from './containers/doctor-default-layout/doctor-default-sidebar/doctor-default-sidebar.component';
import { DoctorProfileSidebarComponent } from './containers/doctor-default-layout/doctor-profile-sidebar/doctor-profile-sidebar.component';
import { DoctorBreadcrumbComponent } from './containers/doctor-default-layout/doctor-breadcrumb/doctor-breadcrumb.component';
import { ModalBaseComponent } from './components/base/modal-base.component';
import { RouterModule } from '@angular/router';
import { AdminBreadcrumbComponent } from './containers/admin-default-layout/admin-breadcrumb/admin-breadcrumb.component';
import { AdminDefaultHeaderBaseComponent } from './containers/admin-default-layout/admin-default-header/admin-default-header-base.component';
import { HttpLoaderFactory } from './configs/http-loader-factory';

import { environment } from 'src/environments/environment';
import { ModalModule } from 'ngx-bootstrap/modal';
import { ApiModule, Configuration } from './generated';
import { AppComponent } from './app.component';
import { Page404Component } from './pages/error/page404/page404.component';
import { ACCESS_TOKEN_KEY } from './services/authentication.service';
import { DataTablesModule } from 'angular-datatables';
import { SsService } from './services/ss.service';

export const getConfiguration = () => {
  return new Configuration({
    basePath: environment.basePath,
    accessToken: getToken
  })
}

export const getToken = () => {
  const token = localStorage.getItem(ACCESS_TOKEN_KEY);
  return token ? token : '';
}

const APP_CONTAINERS = [
  AdminDefaultLayoutComponent,
  AdminDefaultHeaderComponent,
  AdminBreadcrumbComponent,
  AdminDefaultSidebarComponent,
  AdminDefaultFooterComponent,

  DoctorDefaultHeaderBaseComponent,
  DoctorDefaultLayoutComponent,
  DoctorDefaultHeaderComponent,
  DoctorBreadcrumbComponent,
  DoctorProfileSidebarComponent,
  DoctorDefaultSidebarComponent,
  DoctorDefaultFooterComponent,
];

const APP_BASE_COMPONENTS = [
  DoctorDefaultHeaderBaseComponent,
  AdminDefaultHeaderBaseComponent,
  ModalBaseComponent
];

@NgModule({
  declarations: [AppComponent, ...APP_CONTAINERS, ...APP_BASE_COMPONENTS, Page404Component],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    RouterModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    ToastrModule.forRoot(),
    AppRoutingModule,
    SharedComponentsModule,
    NgbModule,
    ModalModule.forRoot(),
    ApiModule.forRoot(getConfiguration),
    DataTablesModule.forRoot()
  ],
  providers: [
    {
      provide: LocationStrategy,
      useClass: HashLocationStrategy,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorInterceptor,
      multi: true
    },
    BsModalService,
    BsModalRef,
    SsService,
    {
      provide: APP_INITIALIZER,
      useFactory: (ds: SsService) => () => ds.init(),
      deps: [SsService],
      multi: true,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
