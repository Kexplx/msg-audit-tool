import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Audit } from 'src/app/core/data/models/audit.model';
import { Router } from '@angular/router';
import { Store } from '@ngxs/store';
import { AuditState } from 'src/app/core/ngxs/audit.state';

@Component({
  selector: 'app-audit-info',
  templateUrl: './audit-info.component.html',
  styleUrls: ['./audit-info.component.scss'],
})
export class AuditInfoComponent implements OnInit {
  audit$: Observable<Audit>;
  constructor(private router: Router, private store: Store) {}

  ngOnInit() {
    const idRegex = /\/audits\/([^\/]*)\/.*/gm;
    const id = idRegex.exec(this.router.url)[1];
    this.audit$ = this.store.select(AuditState.audit(id));
  }
}
