import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { TranslationsBaseDirective } from 'src/app/base-components/translations-base.component';
import { LanguageService } from 'src/app/services/language.service';

@Component({
  selector: 'app-appointment-form',
  templateUrl: './appointment-form.component.html',
  styleUrls: ['./appointment-form.component.scss']
})
export class AppointmentFormComponent extends TranslationsBaseDirective {
  public override translations: any = {
    appointment: {}
  }
  public form: FormGroup = this.formBuilder.group({});
  @Output() save: EventEmitter<any> = new EventEmitter<any>();

  public isLoading: boolean = false;

  constructor(override languageService: LanguageService,
    private formBuilder: FormBuilder) {
    super(languageService);
    this.initForm();
  }

  public initForm(): void {
    this.form = this.formBuilder.group({
      patientName: new FormControl('', Validators.required),
      patientPhone: new FormControl('', Validators.required),
      patientEmail: new FormControl('', [Validators.email, Validators.required]),
      patientMessage: new FormControl('', [Validators.required, Validators.required])
    });
  }

  public saveChanges(): void {
    this.save.emit(this.form.value);
  }
  override getTranslations(): void {
    this.languageService.get('home.pageTitle').subscribe((res: string) => {
      this.translations.pageTitle = res;
    });
    this.languageService.get('appointment.SelectionDeSpecialite').subscribe((res: string) => {
      this.translations.SelectionDeSpecialite = res;
    });
    this.languageService.get('appointment.SelectionDeMedecin').subscribe((res: string) => {
      this.translations.SelectionDeMedecin = res;
    });
    this.languageService.get('appointment.Heure').subscribe((res: string) => {
      this.translations.Heure = res;
    });
    this.languageService.get('appointment.NomComplet').subscribe((res: string) => {
      this.translations.NomComplet = res;
    });
    this.languageService.get('appointment.Telephone').subscribe((res: string) => {
      this.translations.Telephone = res;
    });
    this.languageService.get('appointment.Email').subscribe((res: string) => {
      this.translations.Email = res;
    });
    this.languageService.get('appointment.BesoinsDuPatient').subscribe((res: string) => {
      this.translations.BesoinsDuPatient = res;
    });
    this.languageService.get('appointment.PrendreUnRDV').subscribe((res: string) => {
      this.translations.PrendreUnRDV = res;
    });
  }
}
