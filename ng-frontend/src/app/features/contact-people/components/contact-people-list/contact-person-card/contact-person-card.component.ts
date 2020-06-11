import { Component, OnInit, Input } from '@angular/core';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';
import { Store } from '@ngxs/store';
import { DeleteContactPerson } from 'src/app/core/ngxs/actions/audit.actions';

@Component({
  selector: 'app-contact-person-card',
  templateUrl: './contact-person-card.component.html',
  styleUrls: ['./contact-person-card.component.scss'],
})
export class ContactPersonCardComponent {
  @Input() contactPerson: ContactPerson;

  constructor(private store: Store) {}

  onDelete() {
    this.store.dispatch(new DeleteContactPerson(this.contactPerson));
  }
}
