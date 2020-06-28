import { Component, Input } from '@angular/core';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';

@Component({
  selector: 'app-contact-person-card',
  templateUrl: './contact-person-card.component.html',
  styleUrls: ['./contact-person-card.component.scss'],
})
export class AuditContactPersonCardComponent {
  @Input() contactPerson: ContactPerson;
  constructor() {}
}
