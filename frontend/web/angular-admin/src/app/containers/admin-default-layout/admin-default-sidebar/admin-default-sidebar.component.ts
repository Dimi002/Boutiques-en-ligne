import { Component } from '@angular/core';
import { AdminDefaultHeaderBaseComponent } from '../admin-default-header/admin-default-header-base.component';

@Component({
  selector: 'app-admin-default-sidebar',
  templateUrl: './admin-default-sidebar.component.html',
  styleUrls: ['./admin-default-sidebar.component.scss'],
})
export class AdminDefaultSidebarComponent extends AdminDefaultHeaderBaseComponent {
  override toggleSideBar(): void {
    super.toggleSideBar();
    window.dispatchEvent(new CustomEvent('toggled:sidebar'));
  }
}
