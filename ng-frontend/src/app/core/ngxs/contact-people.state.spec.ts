import { TestBed } from '@angular/core/testing';
import { NgxsModule, Store } from '@ngxs/store';
import { ContactPersonState } from './contact-people.state';
import { ContactPerson } from '../data/models/contact-person.model';
import { CONTACT_PEOPLE } from '../data/examples/contact-people';
import {
  AddContactPerson,
  DeleteContactPerson,
  UpdateContactPerson,
} from './actions/contact-person.action';

const contactPeople = CONTACT_PEOPLE;

describe('ContactPersonState', () => {
  let contactPerson: ContactPerson;
  let store: Store;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [NgxsModule.forRoot([ContactPersonState])],
    });

    store = TestBed.inject(Store);
    contactPerson = contactPeople[0];
  });

  it('should add a contact-person when addContactPerson is dispatched', () => {
    const length = store.selectSnapshot(ContactPersonState.contactPeople)?.length ?? 0;
    store.dispatch(new AddContactPerson(contactPerson));
    const lengthNow = store.selectSnapshot(ContactPersonState.contactPeople)?.length ?? 0;
    expect(lengthNow).toEqual(length + 1);
  });

  it('should delete a contact-person when deleteContactPerson is dispatched', () => {
    store.dispatch(new AddContactPerson(contactPerson));
    const length = store.selectSnapshot(ContactPersonState.contactPeople)?.length ?? 0;

    store.dispatch(new DeleteContactPerson(contactPerson.id));
    const lengthNow = store.selectSnapshot(ContactPersonState.contactPeople)?.length ?? 0;
    expect(lengthNow).toEqual(length - 1);
  });

  it('should update a contact-person when updateContactPerson is dispatched', () => {
    store.dispatch(new AddContactPerson(contactPerson));
    const updatedContactPerson: ContactPerson = { ...contactPerson, firstName: 'UPDATE' };

    store.dispatch(new UpdateContactPerson(contactPerson.id, updatedContactPerson));

    const name = store.selectSnapshot(ContactPersonState.contactPerson(contactPerson.id)).firstName;

    expect(name).toEqual('UPDATE');
  });

  it('it should get a contactPerson by id when valid id is passed', () => {
    store.dispatch(new AddContactPerson(contactPerson));
    const cp = store.selectSnapshot(ContactPersonState.contactPerson(contactPerson.id));
    expect(cp).toBeTruthy();
  });
});
