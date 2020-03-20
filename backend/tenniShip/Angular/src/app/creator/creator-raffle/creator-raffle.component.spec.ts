import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatorRaffleComponent } from './creator-raffle.component';

describe('CreatorRaffleComponent', () => {
  let component: CreatorRaffleComponent;
  let fixture: ComponentFixture<CreatorRaffleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreatorRaffleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatorRaffleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
