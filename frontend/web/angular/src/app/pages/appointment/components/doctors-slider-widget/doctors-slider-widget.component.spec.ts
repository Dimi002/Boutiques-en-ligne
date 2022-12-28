import { TestBed } from "@angular/core/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { ReplaySubject } from "rxjs";
import { ImageService } from "src/app/services/image.service";
import { LanguageService } from "src/app/services/language.service";
import { DoctorsSliderWidgetComponent } from "./doctors-slider-widget.component";

describe('Doctor Slider Widget Component', () => {
    let languageSubject = new ReplaySubject(1);
    const languageService = jasmine.createSpyObj('LanguageService ', { 'get': languageSubject.asObservable(), 'getDeviceLanguage': undefined });
    const imageService = jasmine.createSpyObj('ImageService ', ['']);
    let component: DoctorsSliderWidgetComponent;
    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [
                RouterTestingModule,
            ],
            declarations: [
                DoctorsSliderWidgetComponent
            ],
            providers: [
                { provide: LanguageService, useValue: languageService },
                { provide: ImageService, useValue: imageService }
            ]
        }).compileComponents();
    });

    it('should create doctor slider widget', () => {
        const fixture = TestBed.createComponent(DoctorsSliderWidgetComponent);
        component = fixture.componentInstance;
        expect(component).toBeTruthy();
    });

})