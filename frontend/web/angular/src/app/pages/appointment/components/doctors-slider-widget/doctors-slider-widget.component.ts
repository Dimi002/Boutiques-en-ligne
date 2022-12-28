import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TranslationsBaseDirective } from 'src/app/base-components/translations-base.component';
import { ImageService } from 'src/app/services/image.service';
import { LanguageService } from 'src/app/services/language.service';

@Component({
  selector: 'app-doctors-slider-widget',
  templateUrl: './doctors-slider-widget.component.html',
  styleUrls: ['./doctors-slider-widget.component.scss']
})
export class DoctorsSliderWidgetComponent extends TranslationsBaseDirective {
  public override translations: any = {
    appointment: {}
  }
  @Input() data!: any;
  @Input() showDetailButton: boolean = true;
  @Output() doctorSelect: EventEmitter<any> = new EventEmitter<any>();

  constructor(override languageService: LanguageService,
    public imageService: ImageService) {
    super(languageService);
  }

  public onSelect(): void {
    this.data.selected = true;
    this.doctorSelect.emit(this.data);
  }

  public getSpecialities(specialities: string[] | undefined): string {
    if (specialities) {
      if (specialities.length <= 3)
        return specialities.join(', ');
      return specialities.slice(0, 3).join(', ') + '...'
    }
    return '';
  }
  
  override getTranslations(): void {
    this.languageService.get('home.pageTitle').subscribe((res: string) => {
      this.translations.pageTitle = res;
    });
    this.languageService.get('appointment.Choisir').subscribe((res: string) => {
      this.translations.Choisir = res;
    });
  }  
}
