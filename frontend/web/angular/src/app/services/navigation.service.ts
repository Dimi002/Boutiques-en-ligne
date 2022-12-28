import { Injectable } from '@angular/core';
import { NavigationExtras, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class NavigationService {

  constructor(
    private router: Router,
  ) { }

  goTo(url: string | undefined) {
    if (url)
      this.router.navigate([url]);
  }

  goToWithId(url: string, id: number, uid?: number) {
    const extra: NavigationExtras = {
      queryParams: {
        id: id,
        uid: uid ? uid : null
      }
    };
    this.router.navigate([url], extra);
  }

  goToWithData(url: string, id: number, uid?: number, data?: any) {
    const extra: NavigationExtras = {
      queryParams: {
        id: id,
        uid: uid ? uid : null,
        data: data ? JSON.stringify(data) : null,
      },

    };
    this.router.navigate([url], extra);
  }

  goToWithParams(url: string, params: any, userId: number) {
    const extra: NavigationExtras = {
      queryParams: {
        param: params,
        uid: userId
      }
    };
    this.router.navigate([url], extra);
  }
}
