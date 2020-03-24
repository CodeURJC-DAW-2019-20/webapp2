import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatorTournamentComponent } from './creator-tournament.component';

describe('CreatorTournamentComponent', () => {
  let component: CreatorTournamentComponent;
  let fixture: ComponentFixture<CreatorTournamentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreatorTournamentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatorTournamentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
