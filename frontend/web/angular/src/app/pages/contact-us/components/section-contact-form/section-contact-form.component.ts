import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { TranslationsBaseDirective } from 'src/app/base-components/translations-base.component';
import { Contact, ContactControllerService } from 'src/app/generated';
import { LanguageService } from 'src/app/services/language.service';
import { NotificationService } from 'src/app/services/notification.service';
import { EXCEPTION } from 'src/app/utils/constants';

@Component({
  selector: 'app-section-contact-form',
  templateUrl: './section-contact-form.component.html',
  styleUrls: ['./section-contact-form.component.scss']
})
export class SectionContactFormComponent extends TranslationsBaseDirective {
  public override translations: any = {
    contact: {},
    menu: {}
  }
  public data: { title: string, description: string } = { title: 'Contactez-Nous', description: '' };
  public form: FormGroup = this.formBuilder.group({});

  public isLoading: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    public contactService: ContactControllerService,
    public notifService: NotificationService, override languageService: LanguageService) {
    super(languageService);
    this.initForm();
  }

  public initForm(): void {
    this.form = this.formBuilder.group({
      nom: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      sujet: new FormControl('', Validators.required),
      message: new FormControl('', Validators.required)
    });
  }

  public sendMail(): void {
    const item: { nom: string, email: string, sujet: string, message: string } = this.form.value;
    this.isLoading = true;
    this.contactService.createUsingPOST(item).toPromise().then((res?: Contact) => {
      if (res) {
        this.notifService.success('Votre message a bien été envoyé !');
        this.form.reset();
      }
    }).catch(error => {
      if (error && error?.errorText == EXCEPTION.CONTACT_MUST_BE_NOT_NULL)
        this.notifService.success(EXCEPTION.CONTACT_MUST_BE_NOT_NULL);
    })
      .finally(() => {
        this.isLoading = false;
      })
  }

  override getTranslations(): void {
    this.languageService.get('home.pageTitle').subscribe((res: string) => {
      this.translations.pageTitle = res;
    });
    this.languageService.get('contact.Nom').subscribe((res: string) => {
      this.translations.Nom = res;
    });
    this.languageService.get('contact.Objet').subscribe((res: string) => {
      this.translations.Objet = res;
    });
    this.languageService.get('contact.Envoi').subscribe((res: string) => {
      this.translations.Envoi = res;
    });
    this.languageService.get('menu.ContactezNous').subscribe((res: string) => {
      this.data.title = res;
    });
  }
}
