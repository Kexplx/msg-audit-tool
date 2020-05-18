import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Audit } from 'src/app/data/models/audit.model';
import { Store } from '@ngxs/store';
import { AuditRegistryState } from 'src/app/ngxs/audit-registry.state';
import { ActivatedRoute, Router } from '@angular/router';
import { NbMenuItem } from '@nebular/theme';
import { tap } from 'rxjs/operators';

@Component({
  selector: 'app-audit-overview',
  templateUrl: './audit-overview.component.html',
  styleUrls: ['./audit-overview.component.scss'],
})
export class AuditOverviewComponent implements OnInit {
  audit$: Observable<Audit>;

  constructor(private store: Store, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    const id = this.route.snapshot.params.id;
    this.audit$ = this.store.select(AuditRegistryState.audit(id));

    this.audit$
      .pipe(
        tap(audit => {
          if (!audit) {
            throw Error(`Audit with id: ${id} not found`);
          }
        }),
      )
      .subscribe(
        () => {},
        () => {
          this.router.navigate(['/audits']);
        },
      );
  }
}
