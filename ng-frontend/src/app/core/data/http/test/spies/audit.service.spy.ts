import { AuditService } from '../../audit.service';
import { of } from 'rxjs';
import { AUDITS } from '../dummies/app-models/audits';

export const auditServiceSpy: jasmine.SpyObj<AuditService> = jasmine.createSpyObj('AuditService', [
  'getAudits',
  'getAudit',
  'postAudit',
  'putAudit',
]);

auditServiceSpy.getAudits.and.returnValue(of(AUDITS));
auditServiceSpy.getAudit.and.returnValue(of(AUDITS[0]));
auditServiceSpy.postAudit.and.returnValue(of(AUDITS[0]));
auditServiceSpy.putAudit.and.returnValue(of(AUDITS[1]));
