import { Component, OnInit } from '@angular/core';
import { Store, Select } from '@ngxs/store';
import { AuditState } from 'src/app/core/ngxs/audit.state';
import { Observable } from 'rxjs';
import { Audit } from 'src/app/core/data/models/audit.model';
import { NbMenuItem, NbMenuService } from '@nebular/theme';
import { FactorsPipe } from '../../../pipes/factors.pipe';
import { CriteriaByFactorPipe } from '../../../pipes/facCritByFactor.pipe';
import { AppRouterState } from 'src/app/core/ngxs/app-router.state';

@Component({
  selector: 'app-sidebar-interview-list',
  templateUrl: './sidebar-interview-list.component.html',
  styleUrls: ['./sidebar-interview-list.component.scss'],
})
export class SidebarInterviewListComponent implements OnInit {
  @Select(AppRouterState.auditId) auditId$: Observable<number>;

  audit$: Observable<Audit>;
  items: NbMenuItem[];

  constructor(
    private store: Store,
    private factorsPipe: FactorsPipe,
    private criteriaPipe: CriteriaByFactorPipe,
    private menuService: NbMenuService,
  ) {}

  ngOnInit() {
    this.menuService.onItemClick().subscribe(x => {
      const el = document.getElementById(x.item.data);
      el?.scrollIntoView({ behavior: 'smooth', block: 'center' });
    });

    this.auditId$.subscribe(id => {
      this.audit$ = this.store.select(AuditState.audit(id));
    });

    this.items = [];
    this.audit$.subscribe(audit => {
      if (!audit) return;

      for (const factor of this.factorsPipe.transform(audit.scope)) {
        const criterias = this.criteriaPipe.transform(audit.scope, factor.id);

        const menuItem: NbMenuItem = {
          title: this.cropTitle(factor.name, 25),
          data: factor.id,
        };

        if (criterias.length > 0) {
          menuItem.children = criterias.map(c => {
            return { title: this.cropTitle(c.name, 25), data: c.id };
          });
        }

        this.items.push(menuItem);
      }
    });
  }

  cropTitle(s: string, n: number): string {
    if (s.length < n) return s;

    return s.substr(0, n) + '...';
  }
}
