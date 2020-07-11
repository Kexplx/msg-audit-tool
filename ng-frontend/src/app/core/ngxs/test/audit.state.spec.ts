import { TestBed } from '@angular/core/testing';
import { Store, NgxsModule } from '@ngxs/store';
import { AuditState } from '../audit.state';
import { AuditService } from '../../http/audit.service';
import { of } from 'rxjs';
import { AUDITS_DUMMY } from './dummies/audits';
import { HttpClientModule } from '@angular/common/http';
import { FacCritService } from '../../http/facCrit.service';
import { FACCRITS_DUMMY } from '../../http/test/dummies/faccrits';
import { AddAudit, UpdateAudit, LoadFacCritsByInterviewId } from '../actions/audit.actions';
import { Audit, AuditStatus } from '../../data/models/audit.model';

fdescribe('AuditState', () => {
  let store: Store;
  let auditServiceSpy: jasmine.SpyObj<AuditService>;
  let facCritServiceSpy: jasmine.SpyObj<FacCritService>;

  beforeEach(() => {
    auditServiceSpy = jasmine.createSpyObj('AuditService', [
      'getAudits',
      'getAudit',
      'postAudit',
      'putAudit',
    ]);

    auditServiceSpy.getAudits.and.returnValue(of(AUDITS_DUMMY));
    auditServiceSpy.getAudit.and.returnValue(of(AUDITS_DUMMY[0]));
    auditServiceSpy.postAudit.and.returnValue(of(AUDITS_DUMMY[0]));
    auditServiceSpy.putAudit.and.returnValue(of(AUDITS_DUMMY[1]));

    facCritServiceSpy = jasmine.createSpyObj('FacCritService', [
      'getFacCrits',
      'getFacCritsByInterviewId',
    ]);

    facCritServiceSpy.getFacCrits.and.returnValue(of(FACCRITS_DUMMY));
    facCritServiceSpy.getFacCritsByInterviewId.and.returnValue(of(FACCRITS_DUMMY));

    TestBed.configureTestingModule({
      imports: [NgxsModule.forRoot([AuditState]), HttpClientModule],
      providers: [
        { provide: AuditService, useValue: auditServiceSpy },
        { provide: FacCritService, useValue: facCritServiceSpy },
      ],
    });

    store = TestBed.inject(Store);
  });

  it('#ngxsOnInit calls #getAudits once and fills the state with the results', () => {
    // ngxsOnInit() called by framework...

    const audits = store.selectSnapshot(AuditState.audits);
    expect(audits).toEqual(AUDITS_DUMMY);
    expect(auditServiceSpy.getAudits.calls.count()).toEqual(1);
  });

  it('#addAudit calls #postAudit once and fills the state with the results', () => {
    const auditsOld = store.selectSnapshot(AuditState.audits);
    store.dispatch(new AddAudit({} as Audit));
    const auditsNew = store.selectSnapshot(AuditState.audits);

    expect(auditsNew.length - auditsOld.length).toEqual(1);
    expect(auditsNew.slice(-1)[0]).toEqual(AUDITS_DUMMY[0]);

    expect(auditServiceSpy.postAudit.calls.count()).toEqual(1);
  });

  it('#updateAudit calls #putAudit once and updates the audit in the state', () => {
    const auditsOld = store.selectSnapshot(AuditState.audits);
    store.dispatch(new UpdateAudit(auditsOld[0].id, {} as Audit));
    const auditsNew = store.selectSnapshot(AuditState.audits);

    expect(auditsNew.length - auditsOld.length).toEqual(0);
    expect(auditsNew.slice(-1)[0]).toEqual(AUDITS_DUMMY[1]);

    expect(auditServiceSpy.putAudit.calls.count()).toEqual(1);
  });

  it('#auditsByStatus returns all audits with the given status', () => {
    const audits = store.selectSnapshot(AuditState.audits);
    const auditsWithStatusManually = audits.filter(a => a.status === AuditStatus.Planned);
    const auditsWithStatusFromState = store.selectSnapshot(
      AuditState.auditByStatus(AuditStatus.Planned),
    );

    expect(auditsWithStatusFromState).toEqual(auditsWithStatusManually);
  });

  it('#audit returns an audit by id', () => {
    const audits = store.selectSnapshot(AuditState.audits);
    const audit = store.selectSnapshot(AuditState.audit(audits[0].id));

    expect(audits[0]).toEqual(audit);
  });

  it('#ngxsOnInit calls #getFacCrits once and fills the state with the results', () => {
    // ngxsOnInit() called by framework...

    const facCrits = store.selectSnapshot(AuditState.facCrits);
    expect(facCrits).toEqual(FACCRITS_DUMMY);
    expect(facCritServiceSpy.getFacCrits.calls.count()).toEqual(1);
  });

  it('#facCrit returns a facCrit by id', () => {
    const facCrits = store.selectSnapshot(AuditState.facCrits);
    const facCrit = store.selectSnapshot(AuditState.facCrit(facCrits[0].id));

    expect(facCrits[0]).toEqual(facCrit);
  });

  it('#facCritByReferenceId returns a facCrit by its referenceId', () => {
    const facCrits = store.selectSnapshot(AuditState.facCrits);
    const factor = facCrits.find(fc => !fc.referenceId);
    const criteriaByReferenceIdManually = facCrits.filter(fc => fc.referenceId === factor.id);

    const criteriaByReferenceIdState = store.selectSnapshot(
      AuditState.facCritByReferenceId(factor.id),
    );

    expect(criteriaByReferenceIdManually).toEqual(criteriaByReferenceIdState);
  });
});
