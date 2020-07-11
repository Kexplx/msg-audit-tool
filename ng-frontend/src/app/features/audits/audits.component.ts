import { Component, OnInit } from '@angular/core';
import { Select } from '@ngxs/store';
import { Observable } from 'rxjs';
import { AuditState } from 'src/app/core/ngxs/audit.state';
import { AuditStatus, Audit } from 'src/app/core/data/models/audit.model';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-audit-list',
  templateUrl: './audits.component.html',
  styleUrls: ['./audits.component.scss'],
})
export class AuditsComponent implements OnInit {
  @Select(AuditState.audits) audits$: Observable<Audit[]>;

  activeAudits: Audit[];
  archivedAudits: Audit[];

  ngOnInit() {
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
