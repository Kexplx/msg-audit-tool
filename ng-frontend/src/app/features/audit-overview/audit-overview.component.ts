import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Store, Select } from '@ngxs/store';
import { Audit } from 'src/app/core/data/models/audit.model';
import { AuditState } from 'src/app/core/ngxs/audit.state';
import { AppRouterState } from 'src/app/core/ngxs/app-router.state';

@Component({
  selector: 'app-audit-overview',
  templateUrl: './audit-overview.component.html',
  styleUrls: ['./audit-overview.component.scss'],
})
export class AuditOverviewComponent implements OnInit {
  @Select(AppRouterState.auditId) auditId$: Observable<number>;

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
      this.store.select(AuditState.audit(id)).subscribe(a => (this.audit = a));
    });
  }
}
