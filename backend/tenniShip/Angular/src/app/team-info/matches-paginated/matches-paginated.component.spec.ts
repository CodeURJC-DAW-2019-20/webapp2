import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MatchesPaginatedComponent } from './matches-paginated.component';

describe('MatchesPaginatedComponent', () => {
  let component: MatchesPaginatedComponent;
  let fixture: ComponentFixture<MatchesPaginatedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MatchesPaginatedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MatchesPaginatedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
