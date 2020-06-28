import { Component, OnInit, Input } from '@angular/core';
import { NbMenuService, NbMenuItem, NbMenuBag } from '@nebular/theme';
import { map, filter } from 'rxjs/operators';
import { Store } from '@ngxs/store';
import * as shortid from 'shortid';
import { Audit, AuditStatus } from 'src/app/core/data/models/audit.model';
import { DeleteAudit } from 'src/app/core/ngxs/actions/audit.actions';
import { Navigate } from 'src/app/core/ngxs/actions/router.actions';

enum MenuOptions {
  Edit,
  Delete,
}

@Component({
  selector: 'app-audit-card',
  templateUrl: './audit-card.component.html',
  styleUrls: ['./audit-card.component.scss'],
})
export class AuditCardComponent implements OnInit {
  @Input() audit: Audit;
  nbMenuId: string;
  auditStatus = AuditStatus;
  items: NbMenuItem[] = [
    {
      title: 'Bearbeiten',
      icon: 'edit-outline',
      data: MenuOptions.Edit,
    },
    { title: 'Löschen', icon: 'trash-outline', data: MenuOptions.Delete },
  ];

  constructor(private nbMenuService: NbMenuService, private store: Store) {}

  get contactPerson() {
    return this.audit.contactPeople ? this.audit.contactPeople[0] : null;
  }

  ngOnInit() {
    this.nbMenuId = shortid.generate();

    this.nbMenuService
      .onItemClick()
      .pipe(
        filter(({ tag }) => tag === this.nbMenuId),
        map((menuBag: NbMenuBag) => menuBag.item.data),
      )
      .subscribe((option: MenuOptions) => {
        switch (option) {
          case MenuOptions.Delete:
            this.store.dispatch(new DeleteAudit(this.audit.id));
            break;
          case MenuOptions.Edit:
            this.store.dispatch(new Navigate(`/audits/${this.audit.id}/edit`));
        }
      });
  }
}
