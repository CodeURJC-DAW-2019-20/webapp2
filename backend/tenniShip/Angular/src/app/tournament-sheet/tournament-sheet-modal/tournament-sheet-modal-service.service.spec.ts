import { TestBed } from '@angular/core/testing';

import { TournamentSheetModalServiceService } from './tournament-sheet-modal-service.service';

describe('TournamentSheetModalServiceService', () => {
  let service: TournamentSheetModalServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TournamentSheetModalServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
