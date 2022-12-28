import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { ErrorObject } from '../additional-models/ErrorObject';
import { AuthenticationService } from '../services/authentication.service';
import { NotificationService } from '../services/notification.service';

@Injectable()

/**
 * @description Cette classe a pour role d'intercepter les erreurs lors des requetes (apres la reponse du serveur)
 * et de les afficher dans la console, c'est un filtre Angular
 * @author Arléon Zemtsop
 * @email arleonzemtsop@gmail.com
 */
export class ErrorInterceptor implements HttpInterceptor {

  private static showMessage = true;
  private static showForbiddenMessage = true;

  constructor(
    private notifService: NotificationService,
    private authService: AuthenticationService) { }

  public intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    request = request.clone({
      withCredentials: false
    });
    return next.handle(request)
      .pipe(map((data: HttpEvent<any>) => {
        return data;
      }))
      .pipe(catchError((err) => {
        if (err && err.status === 401 && err.error.errorStatus === '401' && err.error.stringErrorCode === 'ACCOUNT_DESACTIVATED') {
          console.error('Access err: ', err);
          return this.logout();
        } else if (err && err.status === 403 && err.error.errorStatus === '403' && err.error.stringErrorCode === 'TOKEN_EXPIRED') {
          console.error('Session expired err: ', err);
          return this.forbidden();
        }
        return this.errorToMessage(err);
      }));
  }

  public forbidden(): any[] {
    if (ErrorInterceptor.showForbiddenMessage) {
      this.notifService.warning('Votre session a expirée, veuillez vous connecter à nouveau!');
      ErrorInterceptor.showForbiddenMessage = false;
      setTimeout(() => ErrorInterceptor.showForbiddenMessage = true, 3000);
    }
    this.authService.logout();
    return [];
  }

  public logout(): any[] {
    if (ErrorInterceptor.showMessage) {
      this.notifService.warning("Votre compte a été désactivé, veuillez consulter un administateur pour plus de détails!");
    } else {
      ErrorInterceptor.showMessage = true;
    }
    this.authService.logout();
    return [];
  }

  public errorToMessage(err: any): Observable<any> {
    const error: ErrorObject = err.error;
    return throwError(error);
  }

}
