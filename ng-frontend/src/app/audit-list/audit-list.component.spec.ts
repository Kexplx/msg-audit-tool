import { AuditListComponent } from './audit-list.component';
import { TestBed } from '@angular/core/testing';

describe('AuditListComponent', () => {
  let component: AuditListComponent;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AuditListComponent],
    });

    component = TestBed.createComponent(AuditListComponent).componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
