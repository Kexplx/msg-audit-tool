import { State, Selector, Action, StateContext, createSelector, NgxsOnInit } from '@ngxs/store';
import { patch, updateItem, removeItem, append } from '@ngxs/store/operators';
import { Injectable } from '@angular/core';
import { ContactPerson } from '../data/models/contact-person.model';
import { AddContactPerson, UpdateContactPerson } from './actions/contact-person.action';
import { ContactPersonService } from '../http/contact-person.service';

export interface ContactPersonStateModel {
  contactPersons: ContactPerson[];
}

/**
 * State for managing the contact people of the application.
 *
 * Has: Action handlers to read, write, update and delete a contact person.
 * Static and dynamic selectors to select contact people.
 */
@State<ContactPersonStateModel>({
  name: 'contactPerson',
})
@Injectable()
export class ContactPersonState implements NgxsOnInit {
  constructor(private contactPersonService: ContactPersonService) {}

  ngxsOnInit({ patchState }: StateContext<ContactPersonStateModel>) {
    this.contactPersonService.getContactPersons().subscribe(contactPersons => {
      patchState({ contactPersons });
    });
  }

  @Selector()
  static contactPersons(state: ContactPersonStateModel) {
    return state.contactPersons;
  }

  static contactPerson(id: number) {
    return createSelector([ContactPersonState], (state: ContactPersonStateModel) => {
      return state.contactPersons.find(x => x.id === id);
    });
  }

  @Action(AddContactPerson)
  addContactPerson(
    { setState }: StateContext<ContactPersonStateModel>,
    { contactPerson }: AddContactPerson,
  ) {
    this.contactPersonService.postContactPerson(contactPerson).subscribe(contactPerson => {
      setState(
        patch({
          contactPersons: append<ContactPerson>([contactPerson]),
        }),
      );
    });
  }

  @Action(UpdateContactPerson)
  updateContactPerson(
    { setState }: StateContext<ContactPersonStateModel>,
    { id, contactPerson }: UpdateContactPerson,
  ) {
    this.contactPersonService
      .putContactPerson({ ...contactPerson, id })
      .subscribe(contactPerson => {
        setState(
          patch({
            contactPersons: updateItem<ContactPerson>(
              x => x.id === contactPerson.id,
              contactPerson,
            ),
          }),
        );
      });
  }
}
