import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TournamentSheetGroupsComponent } from './tournament-sheet-groups.component';

describe('TournamentSheetGroupsComponent', () => {
  let component: TournamentSheetGroupsComponent;
  let fixture: ComponentFixture<TournamentSheetGroupsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TournamentSheetGroupsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TournamentSheetGroupsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
