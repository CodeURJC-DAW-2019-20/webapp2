import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TournamentSheetTeamsComponent } from './tournament-sheet-teams.component';

describe('TournamentSheetTeamsComponent', () => {
  let component: TournamentSheetTeamsComponent;
  let fixture: ComponentFixture<TournamentSheetTeamsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TournamentSheetTeamsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TournamentSheetTeamsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
