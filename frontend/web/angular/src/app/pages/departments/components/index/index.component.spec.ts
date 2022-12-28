import { TestBed, async, ComponentFixture } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { HttpClientModule } from '@angular/common/http';
import { ToastrModule } from 'ngx-toastr';
import { ApiModule, SpecialityControllerService } from 'src/app/generated';
import { getConfiguration } from 'src/app/app.module';
import { IndexComponent } from './index.component';
import { ImageService } from 'src/app/services/image.service';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { RouterTestingModule } from '@angular/router/testing';

describe('departement index', () => {

    let component: IndexComponent;
    let fixture: ComponentFixture<IndexComponent>

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [IndexComponent],
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
        fixture = TestBed.createComponent(IndexComponent);
        component = fixture.debugElement.componentInstance;
        fixture.detectChanges();
    });

    it('should index component', () => {
        expect(component).toBeTruthy();
    });


    it('should getRouteParams', () => {
        component.getRouteParams()
    });

    it('should get data', () => {
        component.getData("all");
    });


});
