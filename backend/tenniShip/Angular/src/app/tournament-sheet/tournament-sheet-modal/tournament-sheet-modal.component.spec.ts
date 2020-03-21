import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TournamentSheetModalComponent } from './tournament-sheet-modal.component';

describe('TournamentSheetModalComponent', () => {
  let component: TournamentSheetModalComponent;
  let fixture: ComponentFixture<TournamentSheetModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TournamentSheetModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TournamentSheetModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
