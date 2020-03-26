import { TestBed } from '@angular/core/testing';

import { PageLengthService } from './page-length.service';

describe('PageLengthService', () => {
  let service: PageLengthService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PageLengthService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
