import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ContactPerson } from '../models/contact-person.model';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ContactPersonService {
  constructor(private http: HttpClient) {}

  getContactPersons(): Observable<ContactPerson[]> {
    const url = environment.baseUrl + 'contactpersons';

    return this.http.get<ContactPerson[]>(url);
  }

  getContactPerson(id: number): Observable<ContactPerson> {
    const url = environment.baseUrl + 'contactpersons/' + id;

    return this.http.get<ContactPerson>(url);
  }

  postContactPerson(contactPerson: ContactPerson): Observable<ContactPerson> {
    const url = environment.baseUrl + 'contactpersons';

    return this.http.post<ContactPerson>(url, contactPerson);
  }

  putContactPerson(contactPerson: ContactPerson): Observable<ContactPerson> {
    const url = environment.baseUrl + 'contactpersons/' + contactPerson.id;

    return this.http.put<ContactPerson>(url, contactPerson);
  }
}
