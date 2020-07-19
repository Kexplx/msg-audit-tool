import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { AuditStatus, Audit } from 'src/app/core/data/models/audit.model';
import { filter } from 'rxjs/operators';
import { AuditStore } from 'src/app/core/stores/audit.store';

@Component({
  selector: 'app-audit-list',
  templateUrl: './audits.component.html',
  styleUrls: ['./audits.component.scss'],
})
export class AuditsComponent implements OnInit {
  audits$: Observable<Audit[]>;

  constructor(private auditStore: AuditStore) {}

  activeAudits: Audit[];
  archivedAudits: Audit[];

  ngOnInit() {
    this.audits$ = this.auditStore.audits$;
    this.auditStore.loadAudits();

    this.audits$.pipe(filter(audits => audits != undefined)).subscribe(audits => {
      this.activeAudits = audits.filter(
        a => a.status === AuditStatus.Active || a.status === AuditStatus.Planned,
      );

      this.archivedAudits = audits.filter(
        a => a.status === AuditStatus.Finished || a.status === AuditStatus.Cancelled,
      );
    });
  }
}
