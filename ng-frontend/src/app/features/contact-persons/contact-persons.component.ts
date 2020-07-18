import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';
import { ContactPersonNewService } from 'src/app/core/http_new/contact-person-new.service';

@Component({
  selector: 'app-contact-persons-list',
  templateUrl: './contact-persons.component.html',
  styleUrls: ['./contact-persons.component.scss'],
})
export class ContactPersonsComponent implements OnInit {
  contactPersons$: Observable<ContactPerson[]>;

  constructor(private contactPersonService: ContactPersonNewService) {}

  ngOnInit() {
    this.contactPersons$ = this.contactPersonService.contactPersons$;
    this.contactPersonService.getContactPersons();
  }
}
