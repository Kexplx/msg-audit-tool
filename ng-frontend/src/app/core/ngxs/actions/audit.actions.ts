import { Audit } from '../../data/models/audit.model';
import { Interview } from '../../data/models/interview.model';

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

export class AddInterview {
  static readonly type = '[AuditOverview] Add Interview';
  constructor(public audit: Audit, public interview: Interview) {}
}
