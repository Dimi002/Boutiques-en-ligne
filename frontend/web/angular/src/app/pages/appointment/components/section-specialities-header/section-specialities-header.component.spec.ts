import { TestBed } from "@angular/core/testing";
import { SectionSpecialitiesHeaderComponent } from "./section-specialities-header.component";

describe('Section Specialities Component', () => {
    let component: SectionSpecialitiesHeaderComponent;
    beforeEach(async () => {
        await TestBed.configureTestingModule({}).compileComponents();
    });

    it('should section specialities component', () => {
        const fixture = TestBed.createComponent(SectionSpecialitiesHeaderComponent);
        component = fixture.componentInstance;
        expect(component).toBeTruthy();
    });

})