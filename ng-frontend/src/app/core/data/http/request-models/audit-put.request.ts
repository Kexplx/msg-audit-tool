import { AuditStatus } from '../../models/audit.model';

export interface AuditPutRequest {
  name: string;
  startDate: string;
  endDate: string;
  status: AuditStatus;
}
