import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Audit } from 'src/app/core/data/models/audit.model';
import { Store, Select } from '@ngxs/store';
import { AuditState } from 'src/app/core/ngxs/audit.state';
import { AppRouterState } from 'src/app/core/ngxs/app-router.state';

@Component({
  selector: 'app-audit-info',
  templateUrl: './audit-info.component.html',
  styleUrls: ['./audit-info.component.scss'],
})
export class AuditInfoComponent implements OnInit {
  @Select(AppRouterState.auditId) auditId$: Observable<number>;

  audit$: Observable<Audit>;
  constructor(private store: Store) {}

  ngOnInit() {
    this.auditId$.subscribe(id => (this.audit$ = this.store.select(AuditState.audit(id))));
  }
}
