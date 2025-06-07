import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DisplayCartaComponent } from './display-carta.component';

describe('DisplayCartaComponent', () => {
  let component: DisplayCartaComponent;
  let fixture: ComponentFixture<DisplayCartaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DisplayCartaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DisplayCartaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
