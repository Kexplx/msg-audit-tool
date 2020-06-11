import { Component, OnInit } from '@angular/core';
import { Select } from '@ngxs/store';
import { AuditState } from 'src/app/core/ngxs/audit.state';
import { Observable } from 'rxjs';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';
import { ContactPersonState } from 'src/app/core/ngxs/contact-people.state';

@Component({
  selector: 'app-contact-people-list',
  templateUrl: './contact-people-list.component.html',
  styleUrls: ['./contact-people-list.component.scss'],
})
export class ContactPeopleListComponent implements OnInit {
  @Select(ContactPersonState.contactPeople) contactPeople$: Observable<ContactPerson[]>;

  ngOnInit() {}
}
