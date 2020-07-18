import { Component, OnInit, OnDestroy } from '@angular/core';
import { Observable, Subscriber } from 'rxjs';
import { Select } from '@ngxs/store';
import { Audit, AuditStatus } from 'src/app/core/data/models/audit.model';
import { AppRouterState } from 'src/app/core/ngxs/app-router.state';
import { filter, map } from 'rxjs/operators';
import { AudtiNewService } from 'src/app/core/http_new/audit-new.service';
import { SubSink } from 'subsink';

@Component({
  selector: 'app-audit-overview',
  templateUrl: './audit-overview.component.html',
  styleUrls: ['./audit-overview.component.scss'],
})
export class AuditOverviewComponent implements OnInit, OnDestroy {
  @Select(AppRouterState.auditId) auditId$: Observable<number>;

  private readonly subSink = new SubSink();

  auditStatuses = AuditStatus;
  selectedAuditStatus: AuditStatus;
  audit: Audit;

  tabs: any[] = [
    {
      title: 'Interviews',
      icon: 'attach-2-outline',
      route: './interviews',
      responsive: true,
      active: true,
    },
    {
      title: 'Kontakte',
      icon: 'person-outline',
      route: './contact-persons',
      responsive: true,
    },
  ];

  constructor(private auditService: AudtiNewService) {}

  ngOnInit(): void {
    const sub = this.auditId$.subscribe(id => {
      this.auditService.audits$
        .pipe(
          filter(audits => audits != null),
          map(audits => audits.find(a => a.id === id)),
        )
        .subscribe(audit => {
          this.audit = audit;
          this.selectedAuditStatus = audit.status;
        });
    });

    this.subSink.add(sub);
    this.auditService.getAudits();
  }

  ngOnDestroy() {
    this.subSink.unsubscribe();
  }

  onStatusChange() {
    this.auditService.putAudit({ ...this.audit, status: this.selectedAuditStatus });
  }
}
