import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Audit } from 'src/app/core/data/models/audit.model';
import { Store } from '@ngxs/store';
import { AuditState } from 'src/app/core/ngxs/audit.state';
import { IdService } from 'src/app/core/id.service';

@Component({
  selector: 'app-audit-info',
  templateUrl: './audit-info.component.html',
  styleUrls: ['./audit-info.component.scss'],
})
export class AuditInfoComponent implements OnInit {
  audit$: Observable<Audit>;
  constructor(private store: Store, private routesService: IdService) {}

  ngOnInit() {
    this.routesService.auditId$.subscribe(
      id => (this.audit$ = this.store.select(AuditState.audit(id))),
    );
  }
}
