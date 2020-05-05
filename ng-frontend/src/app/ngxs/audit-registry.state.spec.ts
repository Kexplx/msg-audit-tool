import { TestBed } from '@angular/core/testing';
import { NgxsModule, Store, Action } from '@ngxs/store';
import { AuditRegistryState } from './audit-registry.state';
import { Audit } from '../data/models/audit.model';
import { AddAudit, DeleteAudit } from './audit.actions';

describe('AuditRegsitryState', () => {
  let store: Store;
  let audit: Audit;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [NgxsModule.forRoot([AuditRegistryState])],
    });

    store = TestBed.inject(Store);

    audit = {
      name: 'TestAudit',
      contactPerson: {
        firstName: 'Max',
        lastName: 'Meier',
        information: '0192',
        title: 'Herr',
      },
      customerData: {
        department: 'a',
        name: 'b',
        sector: 'c',
      },
    };
  });

  it('should add audit after AddAudit action was dispatched', () => {
    store.dispatch(new AddAudit(audit));
    const selectedAudit = store.selectSnapshot(AuditRegistryState.audits).find(() => audit);

    expect(selectedAudit).toBe(audit);
  });

  it('should delete audit after DeleteAudit action was dispatched', () => {
    store.dispatch(new AddAudit(audit));
    store.dispatch(new DeleteAudit(audit));

    const selectedAudit = store.selectSnapshot(AuditRegistryState.audits).find(() => audit);
    expect(selectedAudit).toBeUndefined();
  });
});
