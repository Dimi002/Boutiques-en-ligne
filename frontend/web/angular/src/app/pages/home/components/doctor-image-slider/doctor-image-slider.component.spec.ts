import { TestBed, async, ComponentFixture } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { DoctorImageSliderComponent } from './doctor-image-slider.component';
import { Specialist } from 'src/app/generated';

describe('Home Index Component', () => {

    let component: DoctorImageSliderComponent;
    let fixture: ComponentFixture<DoctorImageSliderComponent>

    let doctors: Specialist[] = [];
    let e: any;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [
                RouterTestingModule
            ],
            declarations: [
                DoctorImageSliderComponent
            ],
            schemas: [NO_ERRORS_SCHEMA]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(DoctorImageSliderComponent);
        component = fixture.debugElement.componentInstance;

        doctors = [
            {
                appointmentsList: [],
                biography: "mon nom",
                city: "Dschang",
                createdOn: new Date(),
                gender: "male",
                lastUpdateOn: new Date(),
                planings: [],
                socialMediaLinks: "facebook",
                specialistId: 1,
                specialistSpecialityList: [],
                specialitiesList: ["genicologue"],
                status: 1,
                userId: {},
                yearOfExperience: 10,
            },
            {
                appointmentsList: [],
                biography: "mon nom",
                city: "Dschang",
                createdOn: new Date(),
                gender: "male",
                lastUpdateOn: new Date(),
                planings: [],
                socialMediaLinks: "facebook",
                specialistId: 1,
                specialistSpecialityList: [],
                specialitiesList: ["genicologue"],
                status: 1,
                userId: {},
                yearOfExperience: 10,
            }
        ]

        component.doctors = doctors;
        fixture.detectChanges();
    });

    it('should create the doctor-image-slider component', () => {
        expect(component).toBeTruthy();
    });


    it('should verify expected data', () => {

        // the assertions

        expect(component.doctors).toBeDefined()
        expect(component.doctors.length).toBe(2);
        expect(component.doctors[0].biography).toBe('mon nom');
    });

    it('remove Slide', () => {

        // the assertions
        component.removeSlide();
        expect(component.doctors.length).toBe(1);
        expect(component.doctors[0].biography).toBe('mon nom');
    });

    it('add, slick, breakpoint, afterchange, beforechange Slide', () => {

        // the assertions
        component.addSlide();
        component.slickInit(e);
        component.breakpoint(e);
        component.afterChange(e);
        component.beforeChange(e);
        expect(component.doctors.length).toBe(2);
    });

});
