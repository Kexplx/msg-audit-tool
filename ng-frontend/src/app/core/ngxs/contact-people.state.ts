import { State, Selector, Action, StateContext, createSelector } from '@ngxs/store';
import { patch, updateItem, removeItem, append } from '@ngxs/store/operators';
import { Injectable } from '@angular/core';
import * as shortid from 'shortid';
import { ContactPerson } from '../data/models/contact-person.model';
import { CONTACT_PEOPLE } from '../data/examples/contact-people';
import {
  AddContactPerson,
  DeleteContactPerson,
  UpdateContactPerson,
} from './actions/contact-person.action';

export interface ContactPersonStateModel {
  contactPeople: ContactPerson[];
}

/**
 * State for managing the contact people of the application
 *
 * Action handlers to read, write, update and delete a contact person
 * Static and dynamic selectors to select contact people
 */
@State<ContactPersonStateModel>({
  name: 'contactPerson',
  defaults: {
    contactPeople: CONTACT_PEOPLE,
  },
})
@Injectable()
export class ContactPersonState {
  @Selector()
  static contactPeople(state: ContactPersonStateModel) {
    return state.contactPeople;
  }

  static contactPerson(id: string) {
    return createSelector([ContactPersonState], (state: ContactPersonStateModel) => {
      return state.contactPeople.find(x => x.id === id);
    });
  }

  @Action(AddContactPerson)
  addContactPerson(
    { setState }: StateContext<ContactPersonStateModel>,
    { contactPerson }: AddContactPerson,
  ) {
    setState(
      patch({
        contactPeople: append<ContactPerson>([{ ...contactPerson, id: shortid.generate() }]),
      }),
    );
  }

  @Action(DeleteContactPerson)
  deleteContactPerson(
    { setState }: StateContext<ContactPersonStateModel>,
    { contactPerson }: DeleteContactPerson,
  ) {
    setState(
      patch({
        contactPeople: removeItem<ContactPerson>(x => x === contactPerson),
      }),
    );
  }

  @Action(UpdateContactPerson)
  updateContactPerson(
    { setState }: StateContext<ContactPersonStateModel>,
    { id, contactPerson }: UpdateContactPerson,
  ) {
    setState(
      patch({
        contactPeople: updateItem<ContactPerson>(x => x.id === id, { id, ...contactPerson }),
      }),
    );
  }
}
