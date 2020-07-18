import { Component, OnInit } from '@angular/core';
import { Select } from '@ngxs/store';
import { Observable } from 'rxjs';
import { AuditState } from 'src/app/core/ngxs/audit.state';
import { AuditStatus, Audit } from 'src/app/core/data/models/audit.model';
import { filter } from 'rxjs/operators';
import { AudtiNewService } from 'src/app/core/http_new/audit-new.service';

@Component({
  selector: 'app-audit-list',
  templateUrl: './audits.component.html',
  styleUrls: ['./audits.component.scss'],
})
export class AuditsComponent implements OnInit {
  audits$: Observable<Audit[]>;

  constructor(private auditService: AudtiNewService) {}

  activeAudits: Audit[];
  archivedAudits: Audit[];

  ngOnInit() {
    this.audits$ = this.auditService.audits$;
    this.auditService.getAudits();

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
