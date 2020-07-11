import { Audit } from '../../data/models/audit.model';

export class AddAudit {
  static readonly type = '[AuditList] Add Audit';
  constructor(public audit: Audit) {}
}

export class UpdateAudit {
  static readonly type = '[Auditlist] Update Audit';
  constructor(public id: number, public audit: Audit) {}
}

export class LoadFacCritsByInterviewId {
  static readonly type = '[Interview] Load all faccrits by interview id';
  constructor(public id: number) {}
}
