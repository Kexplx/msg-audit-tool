import { Component, OnInit } from '@angular/core';
import { Select } from '@ngxs/store';
import { Observable } from 'rxjs';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';
import { ContactPersonState } from 'src/app/core/ngxs/contact-person.state';

@Component({
  selector: 'app-contact-persons-list',
  templateUrl: './contact-persons.component.html',
  styleUrls: ['./contact-persons.component.scss'],
})
export class ContactPersonsComponent {
  @Select(ContactPersonState.contactPersons) contactPersons$: Observable<ContactPerson[]>;
}
