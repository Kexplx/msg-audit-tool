import { Component, OnInit, Input } from '@angular/core';
import { NbMenuService, NbMenuItem, NbMenuBag } from '@nebular/theme';
import { map, filter } from 'rxjs/operators';
import { Store } from '@ngxs/store';
import * as shortid from 'shortid';
import { Router } from '@angular/router';
import { Audit, AuditStatus } from 'src/app/core/data/models/audit.model';
import { DeleteAudit, UpdateAudit } from 'src/app/core/ngxs/actions/audit.actions';

enum MenuOptions {
  Edit,
  Delete,
  Reactivate,
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
  items: NbMenuItem[];

  constructor(private nbMenuService: NbMenuService, private store: Store, private router: Router) {}

  get contactPerson() {
    return this.audit.contactPeople ? this.audit.contactPeople[0] : null;
  }

  ngOnInit() {
    const status = this.audit.status;

    this.items = [
      status == AuditStatus.Planned || status == AuditStatus.InAction
        ? {
            title: 'Bearbeiten',
            icon: 'edit-outline',
            link: `/audits/${this.audit.id}/edit`,
            data: MenuOptions.Edit,
          }
        : { title: 'Reaktivieren', icon: 'activity-outline', data: MenuOptions.Reactivate },
      { title: 'Löschen', icon: 'trash-outline', data: MenuOptions.Delete },
    ];

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
            this.store.dispatch(new DeleteAudit(this.audit));
            break;
          case MenuOptions.Reactivate:
            const audit = { ...this.audit, status: AuditStatus.InAction };
            this.store.dispatch(new UpdateAudit(this.audit.id, audit));
        }
      });
  }
}
