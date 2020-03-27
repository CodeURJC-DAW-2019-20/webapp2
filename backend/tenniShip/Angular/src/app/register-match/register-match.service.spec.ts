import { TestBed } from '@angular/core/testing';

import { RegisterMatchService } from './register-match.service';

describe('RegisterMatchService', () => {
  let service: RegisterMatchService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RegisterMatchService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
