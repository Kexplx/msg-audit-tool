import { Audit } from '../data/models/audit.model';

export class AddAudit {
  static readonly type = '[AuditList] Add Audit';
  constructor(public audit: Audit) {}
}

export class DeleteAudit {
  static readonly type = '[AuditList] Delete Audit';
  constructor(public audit: Audit) {}
}

export class UpdateAudit {
  static readonly type = '[Auditlist] Update Audit';
  constructor(public id: string, public audit: Audit) {}
}
