import { TestBed } from "@angular/core/testing";
import { SectionDoctorsSliderComponent } from "./section-doctors-slider.component";

describe('Section Doctor Slider', () => {
    let component: SectionDoctorsSliderComponent;
    beforeEach(async () => {
        await TestBed.configureTestingModule({}).compileComponents();
    });

    it('should create section doctor slider component', () => {
        const fixture = TestBed.createComponent(SectionDoctorsSliderComponent);
        component = fixture.componentInstance;
        expect(component).toBeTruthy();
    });

})