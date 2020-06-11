import { Component, OnInit } from '@angular/core';
import { Select } from '@ngxs/store';
import { Observable } from 'rxjs';
import { AuditState } from 'src/app/core/ngxs/audit.state';
import { AuditStatus, Audit } from 'src/app/core/data/models/audit.model';

@Component({
  selector: 'app-audit-list',
  templateUrl: './audit-list.component.html',
  styleUrls: ['./audit-list.component.scss'],
})
export class AuditListComponent implements OnInit {
  @Select(AuditState.auditByStatus(AuditStatus.Planned, AuditStatus.InAction))
  activeAudits$: Observable<Audit[]>;

  @Select(AuditState.auditByStatus(AuditStatus.Cancelled, AuditStatus.Finished))
  archivedAudits$: Observable<Audit[]>;

  ngOnInit() {}
}
