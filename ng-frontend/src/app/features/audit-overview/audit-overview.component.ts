import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Store, Select } from '@ngxs/store';
import { Audit, AuditStatus } from 'src/app/core/data/models/audit.model';
import { AuditState } from 'src/app/core/ngxs/audit.state';
import { AppRouterState } from 'src/app/core/ngxs/app-router.state';
import { UpdateAudit } from 'src/app/core/ngxs/actions/audit.actions';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-audit-overview',
  templateUrl: './audit-overview.component.html',
  styleUrls: ['./audit-overview.component.scss'],
})
export class AuditOverviewComponent implements OnInit {
  @Select(AppRouterState.auditId) auditId$: Observable<number>;

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

  constructor(private store: Store) {}

  ngOnInit(): void {
    this.auditId$.subscribe(id => {
      this.store
        .select(AuditState.audit(id))
        .pipe(filter(audit => audit != undefined))
        .subscribe(a => {
          this.audit = a;
          this.selectedAuditStatus = a.status;
        });
    });
  }

  onStatusChange() {
    this.store.dispatch(
      new UpdateAudit(this.audit.id, { ...this.audit, status: this.selectedAuditStatus }),
    );
  }
}
