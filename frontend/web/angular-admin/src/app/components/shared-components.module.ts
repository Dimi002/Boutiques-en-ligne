import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgxEditorModule } from 'ngx-editor';
import { SectionTitleComponent } from './section-title/section-title.component';
import { FlagComponent } from './flag/flag.component';
import { LogoComponent } from './logo/logo.component';
import { ImageCropperModalComponent } from './image-cropper-modal/image-cropper-modal.component';
import { ImageCropperModule } from 'ngx-image-cropper';
import { LogoMiniComponent } from './logo-mini/logo-mini.component';
import { HttpLoaderFactory } from 'src/app/configs/http-loader-factory';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { DeleteItemModalComponent } from './delete-item-modal/delete-item-modal.component';
import { CustomMultipleSelectComponent } from './custom-multiple-select/custom-multiple-select.component';
import { ConfirmationMessageModalComponent } from './confirmation-message-modal/confirmation-message-modal.component';
import { DisplayImageModalComponent } from './display-image-modal/display-image-modal.component';
import { ReachTextComponent } from './reach-text/reach-text.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CustomImageComponent } from './custom-image/custom-image.component';

@NgModule({
  declarations: [
    SectionTitleComponent,
    FlagComponent,
    LogoComponent,
    LogoMiniComponent,
    ImageCropperModalComponent,
    DeleteItemModalComponent,
    CustomMultipleSelectComponent,
    ConfirmationMessageModalComponent,
    DisplayImageModalComponent,
    ReachTextComponent,
    CustomImageComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ImageCropperModule,
    TranslateModule.forChild({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    NgxEditorModule.forRoot(),
  ],
  exports: [
    SectionTitleComponent,
    FlagComponent,
    LogoComponent,
    CustomMultipleSelectComponent,
    LogoMiniComponent,
    ReachTextComponent,
    CustomImageComponent
  ]
})
export class SharedComponentsModule { }
