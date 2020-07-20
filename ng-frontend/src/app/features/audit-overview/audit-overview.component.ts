import { Component, OnInit, OnDestroy } from '@angular/core';
import { Audit, AuditStatus } from 'src/app/core/data/models/audit.model';
import { filter, map } from 'rxjs/operators';
import { SubSink } from 'subsink';
import { AuditStore } from 'src/app/core/data/stores/audit.store';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-audit-overview',
  templateUrl: './audit-overview.component.html',
  styleUrls: ['./audit-overview.component.scss'],
})
export class AuditOverviewComponent implements OnInit, OnDestroy {
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

  constructor(private auditStore: AuditStore, private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    const auditId: number = +this.activatedRoute.snapshot.params.auditId;
    const auditSub = this.auditStore.audits$
      .pipe(
        filter(audits => audits != null),
        map(audits => audits.find(a => a.id === auditId)),
      )
      .subscribe(audit => {
        this.audit = audit;
        this.selectedAuditStatus = audit.status;
      });

    this.subSink.add(auditSub);
  }

  ngOnDestroy() {
    this.subSink.unsubscribe();
  }

  onStatusChange() {
    this.auditStore.updateAudit({ ...this.audit, status: this.selectedAuditStatus });
  }
}
