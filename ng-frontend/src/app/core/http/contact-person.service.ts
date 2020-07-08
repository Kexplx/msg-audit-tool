import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ContactPerson } from '../data/models/contact-person.model';
import { compileTimeSwitchedString } from './connectionStrings';

@Injectable({
  providedIn: 'root',
})
export class ContactPersonService {
  constructor(private http: HttpClient) {}

  getContactPersons() {
    const url = compileTimeSwitchedString + 'contactpersons';
    return this.http.get<ContactPerson[]>(url);
  }

  postContactPerson(contactPerson: ContactPerson) {
    const url = compileTimeSwitchedString + 'contactpersons';
    return this.http.post<ContactPerson>(url, contactPerson);
  }

  putContactPerson(contactPerson: ContactPerson) {
    const url = compileTimeSwitchedString + 'contactpersons/' + contactPerson.id;
    return this.http.put<ContactPerson>(url, contactPerson);
  }
}
