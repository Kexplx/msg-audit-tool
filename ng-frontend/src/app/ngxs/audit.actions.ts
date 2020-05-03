import { Audit } from '../data/models/audit.model';

export class AddAudit {
  static readonly type = '[AuditList] Add Audit';
  constructor(public audit: Audit) {}
}
