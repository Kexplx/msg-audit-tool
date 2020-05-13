import { Component, OnInit, Input } from '@angular/core';
import { NbMenuService, NbMenuItem, NbMenuBag } from '@nebular/theme';
import { map, filter } from 'rxjs/operators';
import { Audit, AuditStatus } from 'src/app/data/models/audit.model';
import { Store } from '@ngxs/store';
import { DeleteAudit } from 'src/app/ngxs/audit.actions';
import * as shortid from 'shortid';
import { Router } from '@angular/router';

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
    { title: 'Bearbeiten', icon: 'edit-outline', data: MenuOptions.Edit },
    { title: 'Löschen', icon: 'trash-outline', data: MenuOptions.Delete },
  ];

  constructor(private nbMenuService: NbMenuService, private store: Store, private router: Router) {}

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
          case MenuOptions.Edit:
            const urlTree = this.router.createUrlTree([`/audits/${this.audit.id}/edit`]);
            this.router.navigateByUrl(urlTree);
            break;
          case MenuOptions.Delete:
            this.store.dispatch(new DeleteAudit(this.audit));
            break;
        }
      });
  }
}
