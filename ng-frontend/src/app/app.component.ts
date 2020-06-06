import { Component } from '@angular/core';
import { NbSidebarService } from '@nebular/theme';
import { Store, Select } from '@ngxs/store';
import { AuditRegistryState } from './core/ngxs/audit-registry.state';
import { Observable } from 'rxjs';
import { Audit } from './core/data/models/audit.model';
import { ContactPerson } from './core/data/models/contact-person.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  @Select(AuditRegistryState.audits) audits$: Observable<Audit[]>;
  @Select(AuditRegistryState.contactPeople) contactPeople$: Observable<ContactPerson[]>;

  constructor(private sidebarService: NbSidebarService, private store: Store) {}

  toggleSidebar() {
    this.sidebarService.toggle();
  }
}
