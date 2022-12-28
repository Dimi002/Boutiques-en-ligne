import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { LanguageService } from './services/language.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'clinic';

  constructor(
    public languageService: LanguageService,
    public router: Router) {
    this.getLanguage();
    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        window.scrollTo({ left: 0, top: 0 });
        return;
      }
    });
  }

  public getLanguage(): void {
    this.languageService.getDeviceLanguage();
  }
}
