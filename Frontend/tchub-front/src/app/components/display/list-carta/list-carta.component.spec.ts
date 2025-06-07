import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListCartaComponent } from './list-carta.component';

describe('ListCartaComponent', () => {
  let component: ListCartaComponent;
  let fixture: ComponentFixture<ListCartaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListCartaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListCartaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
