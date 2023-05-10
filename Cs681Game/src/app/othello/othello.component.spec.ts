import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OthelloComponent } from './othello.component';

describe('OthelloComponent', () => {
  let component: OthelloComponent;
  let fixture: ComponentFixture<OthelloComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OthelloComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OthelloComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
