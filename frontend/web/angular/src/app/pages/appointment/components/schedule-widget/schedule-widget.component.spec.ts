import { TestBed } from "@angular/core/testing";
import { ReplaySubject } from "rxjs";
import { ScheduleWidgetComponent } from "./schedule-widget.component";

describe('Schedule Widget Component', () => {
    let component: ScheduleWidgetComponent;
    beforeEach(async () => {
        await TestBed.configureTestingModule({}).compileComponents();
    });

    it('should create schedule widget', () => {
        const fixture = TestBed.createComponent(ScheduleWidgetComponent);
        component = fixture.componentInstance;
        expect(component).toBeTruthy();
    });

})