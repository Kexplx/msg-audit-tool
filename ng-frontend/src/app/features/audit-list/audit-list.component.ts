import { Component, OnInit } from '@angular/core';
import { Select } from '@ngxs/store';
import { Observable } from 'rxjs';
import { AuditRegistryState } from 'src/app/core/ngxs/audit-registry.state';
import { AuditStatus, Audit } from 'src/app/core/data/models/audit.model';

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
  d = new Date();
  ngOnInit() {}
}
