import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Audit } from 'src/app/core/data/models/audit.model';
import { filter } from 'rxjs/operators';
import { AuditStore } from 'src/app/core/data/stores/audit.store';

@Component({
  selector: 'app-audit-list',
  templateUrl: './audits.component.html',
  styleUrls: ['./audits.component.scss'],
})
export class AuditsComponent implements OnInit {
  audits$: Observable<Audit[]>;

  constructor(private auditStore: AuditStore) {}

  ngOnInit() {
    this.audits$ = this.auditStore.audits$.pipe(filter(audits => audits != null));
  }
}
