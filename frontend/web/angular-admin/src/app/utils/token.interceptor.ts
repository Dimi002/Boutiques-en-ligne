import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';

import { Observable } from 'rxjs';
import { AuthenticationService } from '../services/authentication.service';
import { STATE } from './constants';
import { NotificationService } from '../services/notification.service';

/**
 * @author Arléon Zemtsop
 * @email arleonzemtsop@gmail.com
 */
@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(
    public authService: AuthenticationService,
    public notifService: NotificationService
  ) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    if (this.authService.getUser() && this.authService.getUser().status !== STATE.STATE_ACTIVATED) {
      this.authService.logout();
      this.notifService.info("Votre compte utilisateur est désactivé, veuillez contacter un administrateur pour plus de détails");
    }

    if (this.authService.getAccessToken()) {
      request = this.addToken(request, this.authService.getAccessToken());
    }

    return next.handle(request);
  }

  private addToken(request: HttpRequest<any>, token: string) {
    return request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }
}
