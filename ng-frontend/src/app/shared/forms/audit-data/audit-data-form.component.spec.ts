import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditDataFormComponent } from './audit-data-form.component';

describe('AuditDataComponent', () => {
  let component: AuditDataFormComponent;
  let fixture: ComponentFixture<AuditDataFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AuditDataFormComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuditDataFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
