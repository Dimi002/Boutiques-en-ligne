import { TestBed, async, ComponentFixture } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { HttpClientModule } from '@angular/common/http';
import { ToastrModule } from 'ngx-toastr';
import { ApiModule, SpecialityControllerService } from 'src/app/generated';
import { getConfiguration } from 'src/app/app.module';
import { ImageService } from 'src/app/services/image.service';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { RouterTestingModule } from '@angular/router/testing';
import { SectionDepartmentDescComponent } from './section-department-desc.component';

describe('section departement desc', () => {

    let component: SectionDepartmentDescComponent;
    let fixture: ComponentFixture<SectionDepartmentDescComponent>

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [SectionDepartmentDescComponent],
            imports: [
                HttpClientModule,
                AppRoutingModule,
                RouterModule,
                RouterTestingModule,
                TranslateModule.forRoot(),
                ToastrModule.forRoot(),
                ApiModule.forRoot(getConfiguration),
            ],
            providers: [
                SpecialityControllerService,
                ImageService,
                {
                    provide: ActivatedRoute,
                    useValue: {
                        snapshot: {
                            paramMap: {
                                get(): string {
                                    return '123';
                                },
                            },
                        },
                    },
                },
            ],
            schemas: [NO_ERRORS_SCHEMA]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(SectionDepartmentDescComponent);
        component = fixture.debugElement.componentInstance;
        fixture.detectChanges();
    });

    it('should section department component', () => {
        expect(component).toBeTruthy();
    });


});
