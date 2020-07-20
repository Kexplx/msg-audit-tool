import { AuditStatus } from '../../models/audit.model';

export interface PutAuditDto {
  name: string;
  startDate: string;
  endDate: string;
  status: AuditStatus;
}
