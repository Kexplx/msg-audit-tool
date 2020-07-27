import { AuditService } from '../../http/audit.service';
import { AUDITS } from '../../http/test/dummies/app-models/audits';
import { of } from 'rxjs';
import { AuditStore } from '../audit.store';
import { filter, skip } from 'rxjs/operators';
import { Audit } from '../../models/audit.model';
import { StoreActionService } from '../store-action.service';

describe('AuditStore', () => {
  let auditStore: AuditStore;
  let auditServiceSpy: jasmine.SpyObj<AuditService>;
  let storeActionServiceSpy: jasmine.SpyObj<StoreActionService>;

  beforeEach(() => {
    auditServiceSpy = jasmine.createSpyObj<AuditService>(['getAudits', 'postAudit', 'putAudit']);
    storeActionServiceSpy = jasmine.createSpyObj<StoreActionService>([
      'notifyLoad',
      'notifyAdd',
      'notifyEdit',
    ]);

    auditStore = new AuditStore(auditServiceSpy, storeActionServiceSpy);
  });

  describe('#loadAudits', () => {
    beforeEach(() => {
      auditServiceSpy.getAudits.and.returnValue(of(AUDITS));
    });

    it('should call #getAudits once', () => {
      auditStore.loadAudits();

      expect(auditServiceSpy.getAudits.calls.count()).toEqual(1);
    });

    it('should set _audits$.value to the response', () => {
      auditStore.audits$.pipe(filter(audits => audits != null)).subscribe(audits => {
        expect(audits).toEqual(AUDITS);
      });

      auditStore.loadAudits();
    });

    it('should call #notifyLoad once', () => {
      auditStore.loadAudits();

      expect(storeActionServiceSpy.notifyLoad.calls.count()).toEqual(1);
    });
  });

  describe('#addAudit', () => {
    let auditResponse: Audit;

    beforeEach(() => {
      auditResponse = AUDITS[0];
      auditServiceSpy.postAudit.and.returnValue(of(auditResponse));
    });

    it('should call #postAudit', () => {
      auditStore.addAudit({} as Audit);

      expect(auditServiceSpy.postAudit.calls.count()).toEqual(1);
    });

    it('should add the added audit to _audits$.value', () => {
      auditStore.audits$.pipe(filter(audits => audits != null)).subscribe(audits => {
        expect(audits[0]).toEqual(auditResponse);
      });

      auditStore.addAudit({} as Audit);
    });

    it('should call #notifyAdd once', () => {
      auditStore.addAudit({} as Audit);

      expect(storeActionServiceSpy.notifyAdd.calls.count()).toEqual(1);
    });
  });

  describe('#updateAudits', () => {
    let auditResponse: Audit;

    beforeEach(() => {
      auditResponse = AUDITS[0];

      auditStore['_audits$'].next([{ ...auditResponse, name: 'OLD VALUE' }]);
      auditServiceSpy.putAudit.and.returnValue(of(auditResponse));
    });

    it('should call #postAudit once', () => {
      auditStore.updateAudit({} as Audit);

      expect(auditServiceSpy.putAudit.calls.count()).toEqual(1);
    });

    it("should update the audit's name at index 0", () => {
      auditStore.audits$.pipe(skip(1)).subscribe(audits => {
        expect(audits[0].name).not.toEqual('OLD VALUE');
      });

      auditStore.updateAudit({} as Audit);
    });

    it('should call #notifyEdit once', () => {
      auditStore.updateAudit({} as Audit);

      expect(storeActionServiceSpy.notifyEdit.calls.count()).toEqual(1);
    });
  });
});
