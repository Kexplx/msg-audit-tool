import { ContactPerson } from '../../data/models/contact-person.model';

export class AddContactPerson {
  static readonly type = '[ContactPeopleList] Add Contact Person';
  constructor(public contactPerson: ContactPerson) {}
}

export class UpdateContactPerson {
  static readonly type = '[ContactPeopleList] Update Contact Person';
  constructor(public id: number, public contactPerson: ContactPerson) {}
}

export class DeleteContactPerson {
  static readonly type = '[ContactPeopleList] Delete Contact Person';
  constructor(public id: number) {}
}
