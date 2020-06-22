import { Component, OnInit, Input } from '@angular/core';
import { Select, Store } from '@ngxs/store';
import { AuditState } from 'src/app/core/ngxs/audit.state';
import { Observable } from 'rxjs';
import { Audit } from 'src/app/core/data/models/audit.model';
import { NbMenuItem } from '@nebular/theme';
import { FactorsPipe } from '../../pipes/factors.pipe';
import { CriteriaByFactorPipe } from '../../pipes/facCritByFactor.pipe';

@Component({
  selector: 'app-sidebar-interview-list',
  templateUrl: './sidebar-interview-list.component.html',
  styleUrls: ['./sidebar-interview-list.component.scss'],
})
export class SidebarInterviewListComponent implements OnInit {
  @Input() url: string;

  audit: Observable<Audit>;

  constructor(
    private store: Store,
    private factorsPipe: FactorsPipe,
    private criteriaPipe: CriteriaByFactorPipe,
  ) {}

  items: NbMenuItem[];

  ngOnInit() {
    const idRegex = /\/audits\/([^\/]*)\/.*/gm;
    const id = idRegex.exec(this.url)[1];

    this.audit = this.store.select(AuditState.audit(id));

    this.items = [];
    this.audit.subscribe(audit => {
      console.log('Hello');
      if (!audit) return;
      console.log('World');

      for (const factor of this.factorsPipe.transform(audit.scope)) {
        const criterias = this.criteriaPipe.transform(audit.scope, factor.id);

        const menuItem: NbMenuItem = {
          title: factor.name,
        };

        if (criterias) {
          menuItem.children = criterias.map(x => {
            return { title: x.name };
          });
        }

        this.items.push(menuItem);
      }
    });
  }
}
