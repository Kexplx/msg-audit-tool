import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Store } from '@ngxs/store';
import { ActivatedRoute, Router } from '@angular/router';
import { tap } from 'rxjs/operators';
import { Audit } from 'src/app/core/data/models/audit.model';
import { AuditState } from 'src/app/core/ngxs/audit.state';

@Component({
  selector: 'app-audit-overview',
  templateUrl: './audit-overview.component.html',
  styleUrls: ['./audit-overview.component.scss'],
})
export class AuditOverviewComponent implements OnInit {
  audit$: Observable<Audit>;
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
    const id = this.route.snapshot.params.id;
    this.audit$ = this.store.select(AuditState.audit(id));

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
