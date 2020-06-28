import { TestBed } from '@angular/core/testing';
import { NgxsModule, Store } from '@ngxs/store';
import { Audit, AuditStatus } from '../data/models/audit.model';
import { AuditState } from './audit.state';
import * as jasmine from 'karma-jasmine';
import { AddAudit, DeleteAudit, AddInterview, UpdateInterview } from './actions/audit.actions';
import { Interview, InterviewStatus } from '../data/models/interview.model';

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

  it('should add interview after AddInterview action was dispatched', () => {
    let auditId: string;
    store.dispatch(new AddAudit(audit)).subscribe((a: Audit) => (auditId = a.id));
    const audit$ = store.select(AuditState.audit(auditId));
    let a: Audit;
    audit$.subscribe(x => (a = x));

    store.dispatch(
      new AddInterview(audit.id, { contactPeople: null, facCrits: null, status: null }),
    );
    audit$.subscribe(x => {
      expect(x.interviews.length).toEqual(1);
    });
  });

  it('should update an interview after UpdateInterivew action was dispatched', () => {
    const interview: Interview = {
      contactPeople: null,
      facCrits: null,
      status: null,
      id: 'test123',
    };
    let auditId: string;

    audit.interviews = [interview];
    store.dispatch(new AddAudit(audit)).subscribe((a: Audit) => (auditId = a.id));

    store
      .dispatch(
        new UpdateInterview(auditId, {
          ...interview,
          status: InterviewStatus.Finished,
        }),
      )
      .subscribe(() => {
        const updatedInterview = store.selectSnapshot(AuditState.interview(interview.id));
        expect(updatedInterview.status).toEqual(InterviewStatus.Finished);
      });
  });

  it('returns a interview when searched by id', () => {
    audit.interviews = [{ id: '123', contactPeople: null, facCrits: null, status: null }];
    store.dispatch(new AddAudit(audit));

    const interview = store.selectSnapshot(AuditState.interview('123'));

    expect(interview).toBeTruthy();
  });
});
