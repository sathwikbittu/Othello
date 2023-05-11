import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GameMovesComponent } from './game-moves.component';

describe('GameMovesComponent', () => {
  let component: GameMovesComponent;
  let fixture: ComponentFixture<GameMovesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GameMovesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GameMovesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
