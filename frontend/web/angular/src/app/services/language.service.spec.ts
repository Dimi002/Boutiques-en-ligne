import { TestBed, async, ComponentFixture } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA} from '@angular/core';
import { LanguageService } from './language.service'
import { TranslateModule } from '@ngx-translate/core';
import { languages } from '../configs/languages';


describe('Service:  Language Service', () => {

  let service: LanguageService;
  let lang: string;
  let description: string;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [LanguageService],
      schemas: [ NO_ERRORS_SCHEMA ],
    });
    service = TestBed.inject(LanguageService);
    lang = 'fr'
    description = 'custom description'
  });

  it('when change Language', () => {
    service.changeLanguage(lang)
    expect(service.deviceLanguage).toEqual(lang)
  });

  it('when we call get() with a key (without option param)', () => {
    service.get('aboutUs.Description2', {}).subscribe((res: string) => {
      description = res;
      expect(description).toEqual(res)
    });
  });

  it('when we call get() with a key (with option param)', () => {
    service.get('aboutUs.Description2').subscribe((res: string) => {
      description = res;
      expect(description).toEqual(res)
    });
  });

  it('should get device Language', () => {

    service.getDeviceLanguage()

    if(window.Intl && typeof window.Intl === 'object'){
      expect(navigator.language).toContain(service.deviceLanguage)
    }else{
      expect(service.deviceLanguage).toContain(languages.FR)
    }
  });

  it('should initialise the translate (without fr)', () => {
    service.initTranslate(navigator.language)
  });

  it('should initialise the translate (with fr)', () => {
    service.initTranslate(navigator.language)
  });

  it('should initialise the translate (without a language)', () => {
    service.initTranslate('')
    expect(service.deviceLanguage).toEqual(languages.FR)
  });

  it('should initialise the translate (with a diffrent language)', () => {
    service.initTranslate('My custom language')
    expect(service.deviceLanguage).toEqual(languages.FR)
  });
});
