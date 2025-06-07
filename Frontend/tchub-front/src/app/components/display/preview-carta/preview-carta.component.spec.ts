import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewCartaComponent } from './preview-carta.component';

describe('PreviewCartaComponent', () => {
  let component: PreviewCartaComponent;
  let fixture: ComponentFixture<PreviewCartaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PreviewCartaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PreviewCartaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
