import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TournamentSheetComponent } from './tournament-sheet.component';

describe('TournamentSheetComponent', () => {
  let component: TournamentSheetComponent;
  let fixture: ComponentFixture<TournamentSheetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TournamentSheetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TournamentSheetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
