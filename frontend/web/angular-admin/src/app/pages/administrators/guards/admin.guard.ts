import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { ROLES } from 'src/app/utils/constants';
import { AuthenticationService } from '../../../services/authentication.service';
import { NavigationService } from '../../../services/navigation.service';
import { NotificationService } from '../../../services/notification.service';

/**
 * @author Arléon Zemtsop
 * @email arleonzemtsop@gmail.com
 */
@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {
  constructor(
    public authService: AuthenticationService,
    public navigationService: NavigationService,
    public notificationService: NotificationService) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const roles = this.authService.getUser().roleList;

    if (roles) {
      if (this.authService.isLoggedIn() && roles.includes(ROLES.ADMIN_ROLE)) {
        return true;
      }
      this.authService.logout();
      this.notificationService.danger('You do not have the necessary authorizations to access this page');
      return false;
    }
    return true;
  }

}
