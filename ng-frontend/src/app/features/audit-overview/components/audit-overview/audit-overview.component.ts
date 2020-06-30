import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Store, Select } from '@ngxs/store';
import { ActivatedRoute, Router } from '@angular/router';
import { tap } from 'rxjs/operators';
import { Audit } from 'src/app/core/data/models/audit.model';
import { AuditState } from 'src/app/core/ngxs/audit.state';
import { Interview } from 'src/app/core/data/models/interview.model';
import { InterviewState } from 'src/app/core/ngxs/interview.state';
import { AppRouterState } from 'src/app/core/ngxs/app-router.state';

@Component({
  selector: 'app-audit-overview',
  templateUrl: './audit-overview.component.html',
  styleUrls: ['./audit-overview.component.scss'],
})
export class AuditOverviewComponent implements OnInit {
  interviews$: Observable<Interview[]>;
  audit$: Observable<Audit>;

  @Select(AppRouterState.auditId) auditId$: Observable<string>;

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
      route: './contact-people',
      responsive: true,
    },
  ];

  constructor(private store: Store, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.auditId$.subscribe(id => {
      this.audit$ = this.store.select(AuditState.audit(id));
      this.interviews$ = this.store.select(InterviewState.interviewsByAuditId(id));
    });
  }
}
