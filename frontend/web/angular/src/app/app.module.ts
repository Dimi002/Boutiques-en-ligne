import { HashLocationStrategy, LocationStrategy, PathLocationStrategy } from '@angular/common';
import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { HttpClient, HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TokenInterceptor } from './utils/token.interceptor';
import { ErrorInterceptor } from './utils/error.interceptor';

// Import containers
import { DefaultFooterComponent, DefaultHeaderComponent, DefaultLayoutComponent } from './containers';
import { SharedComponentsModule } from './components/shared-components.module';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { ToastrModule } from 'ngx-toastr';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { DefaultHeaderAddressComponent } from './containers/default-layout/default-header-address/default-header-address.component';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ACCESS_TOKEN_KEY } from './services/authentication.service';
import { ApiModule, Configuration } from './generated';
import { environment } from 'src/environments/environment';
import { SsService } from './services/ss.service';

const APP_CONTAINERS = [
  DefaultFooterComponent,
  DefaultHeaderAddressComponent,
  DefaultHeaderComponent,
  DefaultLayoutComponent,
];

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, "./assets/i18n/", ".json");
}

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

@NgModule({
  declarations: [ AppComponent, ...APP_CONTAINERS, ],
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
    ApiModule.forRoot(getConfiguration)
  ],
  providers: [
    {
      provide: LocationStrategy,
      useClass: HashLocationStrategy
    },
    SsService,
    {
      provide: APP_INITIALIZER,
      useFactory: (ds: SsService) => () => ds.init(),
      deps: [SsService],
      multi: true,
    },
    /*{
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorInterceptor,
      multi: true
    },*/
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
