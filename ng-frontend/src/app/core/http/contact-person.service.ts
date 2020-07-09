import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ContactPerson } from '../data/models/contact-person.model';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ContactPersonService {
  constructor(private http: HttpClient) {}

  /**
   * Builds an observable for making a GET request to get all contact persons.
   *
   * @returns An Observable of contact persons.
   */
  getContactPersons(): Observable<ContactPerson[]> {
    const url = environment.baseUrl + 'contactpersons';

    return this.http.get<ContactPerson[]>(url);
  }

  /**
   * Builds an observable for making a GET request to get a contact person by id.
   *
   * @returns An Observable of a contact person.
   */
  getContactPerson(id: number): Observable<ContactPerson> {
    const url = environment.baseUrl + 'contactpersons/' + id;

    return this.http.get<ContactPerson>(url);
  }

  /**
   * Builds an observable for making a POST request to create a contact person.
   *
   * @param contactPerson The contact person to create.
   * @returns An Observable of the created contact person.
   */
  postContactPerson(contactPerson: ContactPerson): Observable<ContactPerson> {
    const url = environment.baseUrl + 'contactpersons';

    return this.http.post<ContactPerson>(url, contactPerson);
  }

  /**
   * Builds an observable for making a PUT request to update a contact person.
   *
   * @param contactPerson The contact person to update.
   * @returns An Observable of the updated contact person.
   */
  putContactPerson(contactPerson: ContactPerson): Observable<ContactPerson> {
    const url = environment.baseUrl + 'contactpersons/' + contactPerson.id;

    return this.http.put<ContactPerson>(url, contactPerson);
  }
}
