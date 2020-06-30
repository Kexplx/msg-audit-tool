import { TestBed } from '@angular/core/testing';
import { NgxsModule, Store } from '@ngxs/store';
import { Audit, AuditStatus } from '../data/models/audit.model';
import { AuditState } from './audit.state';
import { AddAudit, DeleteAudit } from './actions/audit.actions';

describe('AuditState', () => {
  let store: Store;
  let audit: Audit;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [NgxsModule.forRoot([AuditState])],
    });

    store = TestBed.inject(Store);

    audit = {
      name: 'TestAudit',
      status: AuditStatus.Planned,
      scope: [],
      startDate: null,
      creationDate: null,
    };
  });

  it('should add audit after AddAudit action was dispatched', () => {
    store.dispatch(new AddAudit(audit));
    const audits = store.selectSnapshot(AuditState.audits);
    const selectedAudit = audits[audits.length - 1];

    expect(selectedAudit).toEqual({ ...audit, id: selectedAudit.id });
  });

  it('should delete audit after DeleteAudit action was dispatched', () => {
    let audits = store.selectSnapshot(AuditState.audits);
    if (audits.length === 0) {
      store.dispatch(new AddAudit(audit));
    }
    const auditToDelete = audits[0];
    store.dispatch(new DeleteAudit(auditToDelete.id));
    let updatedAudits = store.selectSnapshot(AuditState.audits);
    expect(updatedAudits[0]).not.toEqual(auditToDelete);
  });
});
