import { Component, OnInit, Input } from '@angular/core';
import { NbMenuService, NbMenuItem } from '@nebular/theme';
import { map } from 'rxjs/operators';
import { Audit } from 'src/app/data/models/audit.model';
import { Store } from '@ngxs/store';
import { DeleteAudit } from 'src/app/ngxs/audit.actions';

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
  items: NbMenuItem[] = [
    { title: 'Bearbeiten', icon: 'edit-outline', data: MenuOptions.Edit },
    { title: 'Löschen', icon: 'trash-outline', data: MenuOptions.Delete },
  ];

  constructor(private nbMenuService: NbMenuService, private store: Store) {}

  ngOnInit() {
    this.nbMenuService
      .onItemClick()
      .pipe(map(menuBag => menuBag.item.data))
      .subscribe((option: MenuOptions) => {
        switch (option) {
          case MenuOptions.Edit:
            //
            break;
          case MenuOptions.Delete:
            this.store.dispatch(new DeleteAudit(this.audit));
            break;
        }
      });
  }
}
