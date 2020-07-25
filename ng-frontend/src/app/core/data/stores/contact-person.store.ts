import { Injectable } from '@angular/core';
import { ContactPerson } from '../models/contact-person.model';
import { BehaviorSubject } from 'rxjs';
import { ContactPersonService } from '../http/contact-person.service';
import { StoreActionService } from './store-action.service';

@Injectable({
  providedIn: 'root',
})
export class ContactPersonStore {
  private _contactPersons$ = new BehaviorSubject<ContactPerson[]>(null);

  get contactPersons$() {
    return this._contactPersons$.asObservable();
  }

  constructor(
    private contactPersonService: ContactPersonService,
    private storeActionService: StoreActionService,
  ) {}

  loadContactPersons(): void {
    this.contactPersonService.getContactPersons().subscribe(contactPersons => {
      this._contactPersons$.next(contactPersons);

      this.storeActionService.notifyLoad('Kontaktpersonen wurden geladen.');
    });
  }

  addContactPerson(contactPerson: ContactPerson): void {
    this.contactPersonService.postContactPerson(contactPerson).subscribe(contactPerson => {
      const contactPersons = this._contactPersons$.value;

      if (!contactPersons) {
        this._contactPersons$.next([contactPerson]);
      } else {
        this._contactPersons$.next([...contactPersons, contactPerson]);
      }

      this.storeActionService.notifyAdd('Kontaktperson wurde erstellt.');
    });
  }

  updateContactPerson(contactPerson: ContactPerson): void {
    this.contactPersonService.putContactPerson(contactPerson).subscribe(contactPerson => {
      const contactPersons = this._contactPersons$.value;
      const indexOfContactPerson = contactPersons.findIndex(cp => cp.id === contactPerson.id);

      this._contactPersons$.next([
        ...contactPersons.slice(0, indexOfContactPerson),
        contactPerson,
        ...contactPersons.slice(indexOfContactPerson + 1),
      ]);

      this.storeActionService.notifyEdit('Kontaktperson wurde aktualisiert.');
    });
  }
}
