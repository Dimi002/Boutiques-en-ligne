import { TestBed, async, ComponentFixture } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { HttpClientModule } from '@angular/common/http';
import { ToastrModule } from 'ngx-toastr';
import { ApiModule } from 'src/app/generated';
import { getConfiguration } from 'src/app/app.module';
import { SectionDirectorCitationComponent } from './section-director-citation.component';

describe('section director citation', () => {

    let component: SectionDirectorCitationComponent;
    let fixture: ComponentFixture<SectionDirectorCitationComponent>

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [SectionDirectorCitationComponent],
            imports: [
                HttpClientModule,
                TranslateModule.forRoot(),
                ToastrModule.forRoot(),
                ApiModule.forRoot(getConfiguration),
            ],
            providers: [
            ],
            schemas: [NO_ERRORS_SCHEMA]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(SectionDirectorCitationComponent);
        component = fixture.debugElement.componentInstance;
        fixture.detectChanges();
    });

    it('should create the section director citation component', () => {
        expect(component).toBeTruthy();
    });



});
