import { Audit } from '../../data/models/audit.model';
import { Interview } from '../../data/models/interview.model';

export class AddAudit {
  static readonly type = '[AuditList] Add Audit';
  constructor(public audit: Audit) {}
}

export class UpdateAudit {
  static readonly type = '[Auditlist] Update Audit';
  constructor(public id: string, public audit: Audit) {}
}

export class DeleteAudit {
  static readonly type = '[AuditList] Delete Audit';
  constructor(public id: string) {}
}

export class AddInterview {
  static readonly type = '[AuditOverview] Add Interview';
  constructor(public auditId: string, public interview: Interview) {}
}

export class UpdateInterview {
  static readonly type = '[AuditOverview] Update Interview';
  constructor(public auditId: string, public interview: Interview) {}
}
