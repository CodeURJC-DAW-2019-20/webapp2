import { TestBed } from '@angular/core/testing';

import { SelectTournamentService } from './select-tournament.service';

describe('SelectTournamentService', () => {
  let service: SelectTournamentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SelectTournamentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
