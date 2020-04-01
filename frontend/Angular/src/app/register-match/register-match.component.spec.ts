import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterMatchComponent } from './register-match.component';

describe('RegisterMatchComponent', () => {
  let component: RegisterMatchComponent;
  let fixture: ComponentFixture<RegisterMatchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegisterMatchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterMatchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
