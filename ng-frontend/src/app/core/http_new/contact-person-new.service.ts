import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ContactPerson } from '../data/models/contact-person.model';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ContactPersonNewService {
  private _contactPersons$ = new BehaviorSubject<ContactPerson[]>([]);

  get contactPersons$() {
    return this._contactPersons$.asObservable();
  }

  constructor(private http: HttpClient) {}

  getContactPersons(): void {
    const url = environment.baseUrl + 'contactpersons';

    this.http
      .get<ContactPerson[]>(url)
      .subscribe(contactPersons => this._contactPersons$.next(contactPersons));
  }

  postContactPerson(contactPerson: ContactPerson): void {
    const url = environment.baseUrl + 'contactpersons';

    this.http.post<ContactPerson>(url, contactPerson).subscribe(contactPerson => {
      const contactPersons = this._contactPersons$.value;
      this._contactPersons$.next([...contactPersons, contactPerson]);
    });
  }

  putContactPerson(contactPerson: ContactPerson): void {
    const url = environment.baseUrl + 'contactpersons/' + contactPerson.id;

    this.http.put<ContactPerson>(url, contactPerson).subscribe(contactPerson => {
      const contactPersons = this._contactPersons$.value;
      const indexOfContactPerson = contactPersons.findIndex(cp => cp.id === contactPerson.id);

      this._contactPersons$.next([
        ...contactPersons.slice(0, indexOfContactPerson),
        contactPerson,
        ...contactPersons.slice(indexOfContactPerson + 1),
      ]);
    });
  }
}
