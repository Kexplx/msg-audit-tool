import { Component, OnInit } from '@angular/core';
import { ContactPersonState } from '../../ngxs/contact-person.state';
import { Select } from '@ngxs/store';
import { Observable } from 'rxjs';
import { Audit } from '../../data/models/audit.model';
import { NbMenuItem, NbMenuService } from '@nebular/theme';
import { filter } from 'rxjs/operators';
import { ContactPerson } from '../../data/models/contact-person.model';

@Component({
  selector: 'app-sidebar-contact-persons',
  templateUrl: './sidebar-contact-persons.component.html',
  styleUrls: ['./sidebar-contact-persons.component.scss'],
})
export class SidebarContactPersonsComponent implements OnInit {
  @Select(ContactPersonState.contactPersons) contactPersons$: Observable<ContactPerson[]>;

  contactPersons: Audit[];
  items: NbMenuItem[];

  constructor(private menuService: NbMenuService) {}

  ngOnInit() {
    this.menuService.onItemClick().subscribe(x => {
      const el = document.getElementById(x.item.data);
      el?.scrollIntoView({ behavior: 'smooth', block: 'center' });
    });

    this.contactPersons$
      .pipe(filter(contactPersons => contactPersons != undefined))
      .subscribe(contactPersons => {
        this.items = contactPersons.map(cp => ({
          title: cp.forename + ' ' + cp.surname + ' | ' + cp.companyName,
          data: cp.id,
        }));
      });
  }
}
