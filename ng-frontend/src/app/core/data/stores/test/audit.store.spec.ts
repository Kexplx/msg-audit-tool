import { AuditService } from '../../http/audit.service';
import { AUDITS } from '../../http/test/dummies/app-models/audits';
import { of } from 'rxjs';
import { AuditStore } from '../audit.store';
import { filter, first } from 'rxjs/operators';
import { Audit } from '../../models/audit.model';

/**
 * Unit tests for AuditStore.
 *
 * Dummy Data importet from ../../http/test/dummies/app-models/audits.
 */
fdescribe('AuditStore', () => {
  let auditStore: AuditStore;
  let auditServiceSpy: jasmine.SpyObj<AuditService>;

  beforeEach(() => {
    auditServiceSpy = jasmine.createSpyObj<AuditService>(['getAudits', 'postAudit', 'putAudit']);

    auditStore = new AuditStore(auditServiceSpy);
  });

  describe('#loadAudits', () => {
    beforeEach(() => {
      auditServiceSpy.getAudits.and.returnValue(of(AUDITS));
    });

    it('should call #getAudits once', () => {
      auditStore.loadAudits();

      expect(auditServiceSpy.getAudits.calls.count()).toEqual(1);
    });

    it('audits$ should emit the new array of audits', () => {
      auditStore.audits$.pipe(filter(audits => audits != null)).subscribe(audits => {
        expect(audits).toEqual(AUDITS);
      });

      auditStore.loadAudits();
    });
  });

  describe('#addAudit', () => {
    let postAuditResponse: Audit;

    beforeEach(() => {
      postAuditResponse = AUDITS[0];
      auditServiceSpy.postAudit.and.returnValue(of(postAuditResponse));
    });

    it('should call #postAudit', () => {
      auditStore.addAudit({} as Audit);

      expect(auditServiceSpy.postAudit.calls.count()).toEqual(1);
    });

    it('audits$ should emit the previous audits and the new one', () => {
      auditStore.audits$.pipe(filter(audits => audits != null)).subscribe(audits => {
        const lastIndex = audits.length - 1;
        expect(audits[lastIndex]).toEqual(postAuditResponse);
      });

      auditStore.addAudit({} as Audit);
    });
  });

  describe('#updateAudits', () => {
    let putAuditResponse: Audit;

    beforeEach(() => {
      putAuditResponse = AUDITS[1];
      auditServiceSpy.getAudits.and.returnValue(of(AUDITS));
      auditServiceSpy.putAudit.and.returnValue(of(putAuditResponse));
    });

    it('should call #putAudit', () => {
      auditStore.audits$
        .pipe(
          filter(audits => audits != null),
          first(), // Take only first to not get into endless loop.
        )
        .subscribe(audits => {
          const auditToUpdate = audits[0];
          auditStore.updateAudit(auditToUpdate);

          expect(auditServiceSpy.putAudit.calls.count()).toEqual(1);
        });
      auditStore.loadAudits(); // load some audits into _audits.value.
    });

    it('should throw error if _audits$.value is null', () => {
      // _audits$.value is null.
      expect(() => {
        auditStore.updateAudit({} as Audit);
      }).toThrowError("Couldn't find audit to update");
    });
  });
});
