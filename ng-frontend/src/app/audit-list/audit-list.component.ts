import { Component } from '@angular/core';
import { Select } from '@ngxs/store';
import { AuditRegistryState } from '../ngxs/audit-registry.state';
import { Observable } from 'rxjs';
import { Audit } from '../data/models/audit.model';

@Component({
  selector: 'app-audit-list',
  templateUrl: './audit-list.component.html',
  styleUrls: ['./audit-list.component.scss'],
})
export class AuditListComponent {
  constructor() {}

  @Select(AuditRegistryState.audits) audits$: Observable<Audit[]>;
}
