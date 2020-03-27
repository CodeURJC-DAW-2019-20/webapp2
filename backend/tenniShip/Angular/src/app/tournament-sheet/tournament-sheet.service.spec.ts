import { TestBed } from '@angular/core/testing';

import { TournamentSheetService } from './tournament-sheet.service';

describe('TournamentSheetService', () => {
  let service: TournamentSheetService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TournamentSheetService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
