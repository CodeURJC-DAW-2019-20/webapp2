import { TestBed } from '@angular/core/testing';

import { MatchesPaginatedService } from './matches-paginated.service';

describe('MatchesPaginatedService', () => {
  let service: MatchesPaginatedService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MatchesPaginatedService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
