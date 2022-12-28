import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { TranslationsBaseDirective } from 'src/app/base-components/translations-base.component';
import { ImageService } from 'src/app/services/image.service';
import { LanguageService } from 'src/app/services/language.service';
import { NavigationService } from 'src/app/services/navigation.service';
import { SearchService } from 'src/app/services/search.service';
import { SearchResponse } from 'src/app/utils/SearchResponse';
@Component({
  selector: 'app-section-search',
  templateUrl: './section-search.component.html',
  styleUrls: ['./section-search.component.scss']
})
export class SectionSearchComponent extends TranslationsBaseDirective {

  public override translations: any = {
    search: {}
  }
  public keyword: string = '';
  public form: FormGroup = this.fb.group({});
  public response?: SearchResponse[] = [];

  public constructor(
    override languageService: LanguageService,
    public navService: NavigationService,
    private fb: FormBuilder,
    private searchService: SearchService,
    public imageService: ImageService
  ) {
    super(languageService);
    this.form = this.fb.group({
      search: []
    });
  }

  override getTranslations(): void {
    this.languageService.get('home.pageTitle').subscribe((res: string) => {
      this.translations.pageTitle = res;
    });
    this.languageService.get('search.ChercherUnDocteur').subscribe((res: string) => {
      this.translations.ChercherUnDocteur = res;
    });
    this.languageService.get('search.PlaceHolder').subscribe((res: string) => {
      this.translations.PlaceHolder = res;
    });
    this.languageService.get('search.Exemple').subscribe((res: string) => {
      this.translations.Exemple = res;
    });
    this.languageService.get('search.Rechercher').subscribe((res: string) => {
      this.translations.Rechercher = res;
    });
    this.languageService.get('search.Specialite').subscribe((res: string) => {
      this.translations.Specialite = res;
    });
    this.languageService.get('search.Specialiste').subscribe((res: string) => {
      this.translations.Specialiste = res;
    });
  }

  search() {
    if (this.keyword.trim() != '') {
      this.searchService.search(this.keyword).toPromise().then(((res: any) => {
        this.response = res;
      }));
    } else {
      this.response = [];
    }
  }



  navigate(option: SearchResponse): void {
    this.keyword = option.libelle ?? '';
    this.response = [];
    var url = 'take-appointment/' + option.id;
    if (option.type === 1)
      url = 'departments/' + option.libelle;
    this.navService.goTo(url);

  }

  isEmpty(): boolean {
    return (this.response?.length === 0);
  }

  showImage(path: string = '') {
    const img = this.imageService.getCover(path);
    return img

  }


}