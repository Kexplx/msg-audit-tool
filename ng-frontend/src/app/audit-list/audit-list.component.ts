import { Component, OnInit } from '@angular/core';
import { Select } from '@ngxs/store';
import { AuditRegistryState } from '../ngxs/audit-registry.state';
import { Observable } from 'rxjs';
import { Audit, AuditStatus } from '../data/models/audit.model';
import { audits } from '../data/examples/audits';

@Component({
  selector: 'app-audit-list',
  templateUrl: './audit-list.component.html',
  styleUrls: ['./audit-list.component.scss'],
})
export class AuditListComponent implements OnInit {
  @Select(AuditRegistryState.auditByStatus(AuditStatus.IsPlanned, AuditStatus.InAction))
  activeAudits$: Observable<Audit[]>;

  @Select(AuditRegistryState.auditByStatus(AuditStatus.IsCanceled, AuditStatus.IsFinished))
  archivedAudits$: Observable<Audit[]>;

  ngOnInit() {}
}
