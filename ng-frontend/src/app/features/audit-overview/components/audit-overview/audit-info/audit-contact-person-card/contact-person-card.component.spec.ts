import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditContactPersonCardComponent } from './contact-person-card.component';

describe('ContactPersonCardComponent', () => {
  let component: AuditContactPersonCardComponent;
  let fixture: ComponentFixture<AuditContactPersonCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AuditContactPersonCardComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuditContactPersonCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
