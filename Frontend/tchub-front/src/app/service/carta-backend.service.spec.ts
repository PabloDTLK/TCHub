import { TestBed } from '@angular/core/testing';

import { CartaBackendService } from './carta-backend.service';

describe('CartaBackendService', () => {
  let service: CartaBackendService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CartaBackendService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
