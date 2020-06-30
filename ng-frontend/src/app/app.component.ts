import { Component } from '@angular/core';
import { NbSidebarService } from '@nebular/theme';
import { Select } from '@ngxs/store';
import { AuditState } from './core/ngxs/audit.state';
import { Observable } from 'rxjs';
import { Audit } from './core/data/models/audit.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  @Select(AuditState.audits) audits$: Observable<Audit[]>;

  constructor(private sidebarService: NbSidebarService) {}

  toggleSidebar() {
    this.sidebarService.toggle();
  }
}
