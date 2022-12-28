import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { LoginData } from '../additional-models/LoginData';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Setting, User } from '../generated';
import { NavigationService } from './navigation.service';
import { UserWrapper } from '../additional-models/UsersWrapper';
import { ROLES } from '../utils/constants';

export const USER_KEY: string = 'USER_KEY';
export const APPOINTMENT_KEY: string = 'APPOINTMENT_KEY';
export const SETTING_KEY: string = 'SETTING_KEY';
export const ACCESS_TOKEN_KEY: string = 'ACCESS_TOKEN_KEY';
export const TOKEN_EXPIRED_AT_KEY: string = 'TOKEN_EXPIRED_AT_KEY';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(
    private http: HttpClient,
    public navigationService: NavigationService) { }

  login(loginData: LoginData): Observable<UserWrapper> {
    return this.http.post<UserWrapper>(`${environment.basePath}/login`, loginData);
  }

  register(user: User): Observable<any> {
    return this.http.post(`${environment.basePath}/users/createOrUpdateUser`, user);
  }

  public storeUser(user: User): void {
    localStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser(): User {
    return JSON.parse(localStorage.getItem(USER_KEY)!);
  }

  public deleteUser(): void {
    localStorage.removeItem(USER_KEY);
  }

  public storeAppointment(appointment: any): void {
    localStorage.setItem(APPOINTMENT_KEY, JSON.stringify(appointment));
  }

  public getAppointment(): User {
    return JSON.parse(localStorage.getItem(APPOINTMENT_KEY)!);
  }

  public deleteAppointment(): void {
    localStorage.removeItem(APPOINTMENT_KEY);
  }

  public storeSettings(setting: Setting): void {
    localStorage.setItem(SETTING_KEY, JSON.stringify(setting));
  }

  public getSettings(): Setting {
    return JSON.parse(localStorage.getItem(SETTING_KEY)!);
  }

  public deleteSettings(): void {
    localStorage.removeItem(SETTING_KEY);
  }

  public storeAccessToken(token: string): void {
    localStorage.setItem(ACCESS_TOKEN_KEY, token);
  }

  public getAccessToken(): string {
    return localStorage.getItem(ACCESS_TOKEN_KEY)!;
  }

  public storeTokenExpiresAt(tokenExpiresAt: string): void {
    localStorage.setItem(TOKEN_EXPIRED_AT_KEY, tokenExpiresAt);
  }

  public deleteTokenExpiresAt(): void {
    localStorage.removeItem(TOKEN_EXPIRED_AT_KEY);
  }

  public getTokenExpiresAt(): string {
    return localStorage.getItem(TOKEN_EXPIRED_AT_KEY)!;
  }

  public deleteAccessToken(): void {
    localStorage.removeItem(ACCESS_TOKEN_KEY);
  }

  public localLogin(user: User, token: string, expiresAt: string): void {
    this.storeUser(user);
    this.storeAccessToken(token);
    this.storeTokenExpiresAt(expiresAt);
  }

  public logout(): void {
    this.deleteUser();
    this.deleteAccessToken();
    this.deleteTokenExpiresAt();
    this.navigationService.goTo('/login');
  }

  public isLoggedIn(): boolean {
    const user: User = this.getUser();
    const token: string = this.getAccessToken();
    const tokenExpiresAt: string = this.getTokenExpiresAt();
    const now = (new Date()).getTime();
    if (user && token && tokenExpiresAt) {
      const expires_at = (new Date(tokenExpiresAt)).getTime();
      return (now < expires_at);
    } else {
      return false;
    }
  }

  public hasAnyRole(roles: string[]): boolean {
    if (this.isLoggedIn()) {
      const userRoles: string[] = this.getUser().roleList!;
      for (let index = 0; index < userRoles.length; index++) {
        if (roles.includes(userRoles[index].trim())) {
          return true;
        }
      }
    } else {
      return false;
    }
    return false;
  }

  public hasAnyPermission(permissions: string[]): boolean {
    if (this.isLoggedIn()) {
      const roleList: string[] = this.getUser().roleList!;
      if (roleList.includes(ROLES.SUPER_ADMIN_ROLE)) {
        return true;
      }
      const userPermissions: string[] = this.getUser().permissionList!;
      for (let index = 0; index < userPermissions.length; index++) {
        if (permissions.includes(userPermissions[index].trim())) {
          return true;
        }
      }
    } else {
      return false;
    }
    return false;
  }

}
