import { TestBed } from '@angular/core/testing';
import { NgxsModule, Store, Action } from '@ngxs/store';
import { AuditRegistryState } from './audit-registry.state';
import { Audit, AuditStatus } from '../data/models/audit.model';
import { AddAudit, DeleteAudit } from './audit.actions';
import { CONSOLE_APPENDER } from 'karma/lib/constants';

describe('AuditRegistryState', () => {
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
        firstName: 'Peter',
        lastName: 'Meier',
        information: '0192',
        title: 'Herr',
        salutation: 'Dr',
      },
      customerData: {
        department: 'a',
        name: 'b',
        sector: 'c',
      },
      status: AuditStatus.IsPlanned,
      creationDate: new Date(2020, 6, 1).getTime(),
    };
  });

  it('should add audit after AddAudit action was dispatched', () => {
    store.dispatch(new AddAudit(audit));
    const audits = store.selectSnapshot(AuditRegistryState.audits);
    const selectedAudit = audits[audits.length - 1];

    expect(selectedAudit).toEqual({ ...audit, id: selectedAudit.id });
  });

  it('should delete audit after DeleteAudit action was dispatched', () => {
    let audits = store.selectSnapshot(AuditRegistryState.audits);
    if (!audits) {
      store.dispatch(new AddAudit(audit));
    }
    const auditToDelete = audits[0];
    store.dispatch(new DeleteAudit(auditToDelete));
    let updatedAudits = store.selectSnapshot(AuditRegistryState.audits);
    expect(updatedAudits[0]).not.toEqual(auditToDelete);
  });
});
