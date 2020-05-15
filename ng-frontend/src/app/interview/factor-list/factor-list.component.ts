import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Audit } from 'src/app/data/models/audit.model';
import { Store } from '@ngxs/store';
import { AuditRegistryState } from 'src/app/ngxs/audit-registry.state';
import { ActivatedRoute } from '@angular/router';
import { NbMenuItem } from '@nebular/theme';

@Component({
  selector: 'app-factor-list',
  templateUrl: './factor-list.component.html',
  styleUrls: ['./factor-list.component.scss'],
})
export class FactorListComponent implements OnInit {
  audit$: Observable<Audit>;
  items: NbMenuItem[] = [
    {
      title: 'Profile',
      expanded: true,
      children: [
        {
          title: 'Change Password',
        },
        {
          title: 'Privacy Policy',
        },
        {
          title: 'Logout',
        },
      ],
    },
    {
      title: 'Shopping Bag',
    },
    {
      title: 'Orders',
    },
  ];

  constructor(private store: Store, private route: ActivatedRoute) {}

  constructMenu(factors) {
    let menu = [];
    factors.forEach(factor => {
      const menuItem = { title: factor.title };
      const categories = factor.categories;
      if (categories) {
        menuItem['children'] = [];
        categories.forEach(category => {
          menuItem['children'].push({ title: category.title });
        });
      }
      menu.push(menuItem);
    });
    return menu;
  }

  ngOnInit(): void {
    const id = this.route.snapshot.params.id;
    this.audit$ = this.store.select(AuditRegistryState.audit(id));

    const audits = this.store.selectSnapshot(AuditRegistryState.audit(id));
    this.items = this.constructMenu(audits.factors);
  }
}
