import { Component } from '@angular/core';
import { NbSidebarService } from '@nebular/theme';
import { Store, Select } from '@ngxs/store';
import { AuditState } from './core/ngxs/audit.state';
import { Observable } from 'rxjs';
import { Audit } from './core/data/models/audit.model';
import { ContactPerson } from './core/data/models/contact-person.model';
import { ContactPersonState } from './core/ngxs/contact-people.state';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  @Select(AuditState.audits) audits$: Observable<Audit[]>;

  constructor(private sidebarService: NbSidebarService, private store: Store) {}

  toggleSidebar() {
    this.sidebarService.toggle();
  }
}
