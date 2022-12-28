import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/index';
import { AuthenticationService } from '../services/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class LoginGuard implements CanActivate {
  constructor(private _authService: AuthenticationService, private _router: Router) { }
  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    if (this._authService.isLoggedIn()) {
      if (this._authService.hasAnyRole(['ADMIN']) && !this._authService.hasAnyRole(['SPECIALIST'])) {
        this._router.navigate(['home/admin-dashboard']);
      }
      if (this._authService.hasAnyRole(['ADMIN']) && this._authService.hasAnyRole(['SPECIALIST'])) {
        this._router.navigate(['home/doctor-dashboard']);
      }
      if (!this._authService.hasAnyRole(['ADMIN']) && !this._authService.hasAnyRole(['SPECIALIST'])) {
        this._router.navigate(['/404']);
      }
      if (this._authService.hasAnyRole(['SPECIALIST']) && !this._authService.hasAnyRole(['ADMIN'])) {
        this._router.navigate(['home/doctor-dashboard']);
      }
      return false;
    } else {
      return true;
    }
  }

}
