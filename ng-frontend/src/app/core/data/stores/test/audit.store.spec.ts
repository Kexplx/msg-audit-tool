import { AuditService } from '../../http/audit.service';
import { AUDITS } from '../../http/test/dummies/app-models/audits';
import { of, timer } from 'rxjs';
import { AuditStore } from '../audit.store';
import { filter, first } from 'rxjs/operators';
import { Audit } from '../../models/audit.model';
import { StoreActionService, StoreActionType } from '../store-action.service';

/**
 * Unit tests for AuditStore.
 *
 * Dummy Data importet from ../../http/test/dummies/app-models/audits.
 */
fdescribe('AuditStore', () => {
  let auditStore: AuditStore;
  let auditServiceSpy: jasmine.SpyObj<AuditService>;
  let storeActionServiceSpy: jasmine.SpyObj<StoreActionService>;

  beforeEach(() => {
    auditServiceSpy = jasmine.createSpyObj<AuditService>(['getAudits', 'postAudit', 'putAudit']);
    storeActionServiceSpy = jasmine.createSpyObj<StoreActionService>(['notify']);

    auditStore = new AuditStore(auditServiceSpy, storeActionServiceSpy);
  });

  describe('#loadAudits', () => {
    beforeEach(() => {
      auditServiceSpy.getAudits.and.returnValue(of(AUDITS));
    });

    it('should call #notify once', () => {
      auditStore.loadAudits();

      expect(storeActionServiceSpy.notify.calls.count()).toEqual(1);
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

    it('should call #notify once', () => {
      auditStore.addAudit({} as Audit);

      expect(storeActionServiceSpy.notify.calls.count()).toEqual(1);
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

    it('should call #putAudit and #notify', () => {
      auditStore.audits$
        .pipe(
          filter(audits => audits != null),
          first(), // Take only first to not get into endless loop.
        )
        .subscribe(audits => {
          const auditToUpdate = audits[0];
          auditStore.updateAudit(auditToUpdate);

          expect(auditServiceSpy.putAudit.calls.count()).toEqual(1);
          expect(storeActionServiceSpy.notify.calls.count()).toEqual(1);
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
