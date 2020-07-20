import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Audit } from 'src/app/core/data/models/audit.model';
import { IdService } from 'src/app/core/id.service';
import { AuditStore } from 'src/app/core/stores/audit.store';
import { filter, map } from 'rxjs/operators';

@Component({
  selector: 'app-audit-info',
  templateUrl: './audit-info.component.html',
  styleUrls: ['./audit-info.component.scss'],
})
export class AuditInfoComponent implements OnInit {
  audit$: Observable<Audit>;
  constructor(private auditStore: AuditStore, private routesService: IdService) {}

  ngOnInit() {
    this.routesService.auditId$.subscribe(id => {
      this.audit$ = this.auditStore.audits$.pipe(
        filter(audits => audits != null),
        map(audits => audits.find(a => a.id === id)),
      );
    });
  }
}
