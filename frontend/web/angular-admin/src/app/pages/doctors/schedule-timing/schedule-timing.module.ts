import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ModalModule } from 'ngx-bootstrap/modal';
import { ScheduleTimingRoutingModule } from './schedule-timing-routing.module';
import { IndexComponent } from './components/index/index.component';
import { ScheduleMainCardComponent } from './components/schedule-main-card/schedule-main-card.component';
import { TimingSlotDurationComponent } from './components/timing-slot-duration/timing-slot-duration.component';
import { ScheduleWidgetComponent } from './components/schedule-timings-widget/schedule-widget/schedule-widget.component';
import { ScheduleWidgetNavComponent } from './components/schedule-timings-widget/schedule-widget-nav/schedule-widget-nav.component';
import { ScheduleWidgetTimeSlotComponent } from './components/schedule-timings-widget/schedule-widget-time-slot/schedule-widget-time-slot.component';
import { ScheduleWidgetTimeSlotsComponent } from './components/schedule-timings-widget/schedule-widget-time-slots/schedule-widget-time-slots.component';
import { EditTimeSlotModalComponent } from './components/edit-time-slot-modal/edit-time-slot-modal.component';
import { AddTimeSlotModalComponent } from './components/add-time-slot-modal/add-time-slot-modal.component';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpLoaderFactory } from 'src/app/configs/http-loader-factory';

@NgModule({
  declarations: [
    IndexComponent,
    ScheduleMainCardComponent,
    TimingSlotDurationComponent,
    ScheduleWidgetComponent,
    ScheduleWidgetNavComponent,
    ScheduleWidgetTimeSlotComponent,
    ScheduleWidgetTimeSlotsComponent,
    EditTimeSlotModalComponent,
    AddTimeSlotModalComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ScheduleTimingRoutingModule,
    ModalModule.forRoot(),
    TranslateModule.forChild({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
  ]
})
export class ScheduleTimingModule { }
