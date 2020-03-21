import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TournamentSheetFinalComponent } from './tournament-sheet-final.component';

describe('TournamentSheetFinalComponent', () => {
  let component: TournamentSheetFinalComponent;
  let fixture: ComponentFixture<TournamentSheetFinalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TournamentSheetFinalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TournamentSheetFinalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
