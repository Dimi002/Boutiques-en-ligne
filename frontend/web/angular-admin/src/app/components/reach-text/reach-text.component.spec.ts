import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReachTextComponent } from './reach-text.component';

describe('ReachTextComponent', () => {
  let component: ReachTextComponent;
  let fixture: ComponentFixture<ReachTextComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReachTextComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReachTextComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
