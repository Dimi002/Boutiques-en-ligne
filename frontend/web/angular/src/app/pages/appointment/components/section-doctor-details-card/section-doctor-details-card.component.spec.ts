import { TestBed } from "@angular/core/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { ReplaySubject } from "rxjs";
import { AuthenticationService } from "src/app/services/authentication.service";
import { ImageService } from "src/app/services/image.service";
import { LanguageService } from "src/app/services/language.service";
import { SectionDoctorDetailsCardComponent } from "./section-doctor-details-card.component";

describe('Section Doctor Details Component', () => {
    let languageSubject = new ReplaySubject(1);
    const languageService = jasmine.createSpyObj('LanguageService ', { 'get': languageSubject.asObservable(), 'getDeviceLanguage': undefined });
    const authService = jasmine.createSpyObj('AuthenticationService ', ['getSettings']);
    const imageService = jasmine.createSpyObj('ImageService ', ['']);
    let component: SectionDoctorDetailsCardComponent;
    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [
                RouterTestingModule,
            ],
            declarations: [
                SectionDoctorDetailsCardComponent
            ],
            providers: [
                { provide: LanguageService, useValue: languageService },
                { provide: AuthenticationService, useValue: authService },
                { provide: ImageService, useValue: imageService }
            ]
        }).compileComponents();
    });

    it('should create section doctor details card component', () => {
        const fixture = TestBed.createComponent(SectionDoctorDetailsCardComponent);
        component = fixture.componentInstance;
        expect(component).toBeTruthy();
    });

})