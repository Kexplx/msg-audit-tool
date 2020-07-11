import { Component, OnInit } from '@angular/core';
import { Select } from '@ngxs/store';
import { AuditState } from '../../ngxs/audit.state';
import { Observable } from 'rxjs';
import { Audit } from '../../data/models/audit.model';
import { NbMenuItem, NbMenuService } from '@nebular/theme';
import { filter } from 'rxjs/operators';
import { parseTimestamp } from '../../data/helpers/date-helpers';

@Component({
  selector: 'app-sidebar-audits',
  templateUrl: './sidebar-audits.component.html',
  styleUrls: ['./sidebar-audits.component.scss'],
})
export class SidebarAuditsComponent implements OnInit {
  @Select(AuditState.audits) audits$: Observable<Audit[]>;

  audits: Audit[];
  items: NbMenuItem[];

  constructor(private menuService: NbMenuService) {}

  ngOnInit() {
    this.menuService.onItemClick().subscribe(x => {
      const el = document.getElementById(x.item.data);
      el?.scrollIntoView({ behavior: 'smooth', block: 'center' });
    });

    this.audits$.pipe(filter(audits => audits != undefined)).subscribe(audits => {
      this.items = audits.map(a => ({
        title: a.name + ' | ' + parseTimestamp(a.startDate),
        data: a.id,
      }));
    });
  }
}
