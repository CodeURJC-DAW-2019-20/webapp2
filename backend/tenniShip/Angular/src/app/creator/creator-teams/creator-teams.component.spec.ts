import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatorTeamsComponent } from './creator-teams.component';

describe('CreatorTeamsComponent', () => {
  let component: CreatorTeamsComponent;
  let fixture: ComponentFixture<CreatorTeamsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreatorTeamsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatorTeamsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
