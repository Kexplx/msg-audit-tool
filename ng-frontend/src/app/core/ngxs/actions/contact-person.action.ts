import { ContactPerson } from '../../data/models/contact-person.model';

export class AddContactPerson {
  static readonly type = '[ContactPersonsList] Add Contact Person';
  constructor(public contactPerson: ContactPerson) {}
}

export class UpdateContactPerson {
  static readonly type = '[ContactPersonsList] Update Contact Person';
  constructor(public id: number, public contactPerson: ContactPerson) {}
}

export class DeleteContactPerson {
  static readonly type = '[ContactPersonsList] Delete Contact Person';
  constructor(public id: number) {}
}
