import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Store } from '@ngxs/store';
import { ActivatedRoute, Router } from '@angular/router';
import { NbMenuItem } from '@nebular/theme';
import { tap } from 'rxjs/operators';
import * as shortid from 'shortid';
import { Audit } from 'src/app/core/data/models/audit.model';
import { AuditRegistryState } from 'src/app/core/ngxs/audit-registry.state';

enum MenuOptions {
  Edit,
}

@Component({
  selector: 'app-audit-overview',
  templateUrl: './audit-overview.component.html',
  styleUrls: ['./audit-overview.component.scss'],
})
export class AuditOverviewComponent implements OnInit {
  audit$: Observable<Audit>;
  items: NbMenuItem[];
  menuId: string;
  tabs: any[] = [
    {
      title: 'Users',
      icon: 'person',
      route: './tab1',
    },
    {
      title: 'Orders',
      icon: 'paper-plane-outline',
      responsive: true,
      route: ['./tab2'],
    },
    {
      title: 'Transaction',
      icon: 'flash-outline',
      responsive: true,
      disabled: true,
    },
  ];

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

    this.menuId = shortid.generate();
    this.items = [
      {
        title: 'Bearbeiten',
        icon: 'edit-outline',
        data: MenuOptions.Edit,
        link: `audits/${id}/overview/edit`,
      },
    ];
  }
}
