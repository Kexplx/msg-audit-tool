import { Audit } from '../data/models/audit.model';
import { Interview } from '../data/models/interview.model';
import { ContactPerson } from '../data/models/contact-person.model';

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

export class AddContactPerson {
  static readonly type = '[ContactPeopleList] Add Contact Person';
  constructor(public contactPerson: ContactPerson) {}
}

export class UpdateContactPerson {
  static readonly type = '[ContactPeopleList] Update Contact Person';
  constructor(public id: string, public contactPerson: ContactPerson) {}
}

export class DeleteContactPerson {
  static readonly type = '[ContactPeopleList] Delete Contact Person';
  constructor(public contactPerson: ContactPerson) {}
}
