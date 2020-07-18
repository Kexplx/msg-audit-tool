import { AuditStatus } from '../../data/models/audit.model';

export interface PutAuditDto {
  name: string;
  startDate: string;
  endDate: string;
  status: AuditStatus;
}
