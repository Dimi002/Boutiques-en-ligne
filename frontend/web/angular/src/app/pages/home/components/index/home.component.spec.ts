import { getConfiguration } from 'src/app/app.module';
import { TestBed, ComponentFixture, async } from '@angular/core/testing';

import { HomeComponent } from './home.component';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { SpecialityMin, SpecialityControllerService, ApiModule, SettingControllerService } from 'src/app/generated';
import { HttpClientModule } from '@angular/common/http';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { NotificationService } from 'src/app/services/notification.service';
import { ToastrModule } from 'ngx-toastr';

describe('Component: Home-Index', () => {

  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [HomeComponent],
      imports: [
        HttpClientModule,
        ToastrModule.forRoot(),
        ApiModule.forRoot(getConfiguration),
      ],
      providers: [
        SettingControllerService,
        AuthenticationService,
        NotificationService,
      ],
      schemas: [NO_ERRORS_SCHEMA]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.debugElement.componentInstance;

    fixture.detectChanges();
  });


  it('should create the Home-Index component', () => {
    expect(component).toBeTruthy();
  });

  it('should initialisation', () => {
    component._initialisation()
  });

});
