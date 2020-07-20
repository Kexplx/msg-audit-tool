import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';
import { ContactPersonStore } from 'src/app/core/data/stores/contact-person.store';

@Component({
  selector: 'app-contact-persons-list',
  templateUrl: './contact-persons.component.html',
  styleUrls: ['./contact-persons.component.scss'],
})
export class ContactPersonsComponent implements OnInit {
  contactPersons$: Observable<ContactPerson[]>;

  constructor(private contactPersonStore: ContactPersonStore) {}

  ngOnInit() {
    this.contactPersons$ = this.contactPersonStore.contactPersons$;
  }
}
