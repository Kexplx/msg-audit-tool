import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Store, Select } from '@ngxs/store';
import { NbMenuService, NbMenuItem } from '@nebular/theme';
import { AuditState } from 'src/app/core/ngxs/audit.state';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';
import { AppRouterState } from 'src/app/core/ngxs/app-router.state';

@Component({
  selector: 'app-sidebar-interview',
  templateUrl: './sidebar-interview.component.html',
  styleUrls: ['./sidebar-interview.component.scss'],
})
export class SidebarInterviewComponent implements OnInit {
  @Select(AppRouterState.facCritId) facCritId$: Observable<string>;
  facCrit$: Observable<FacCrit>;
  items: NbMenuItem[];

  constructor(private store: Store, private menuService: NbMenuService) {}

  ngOnInit() {
    this.items = [];

    this.menuService.onItemClick().subscribe(x => {
      const el = document.getElementById(x.item.data);
      el?.scrollIntoView({ behavior: 'smooth', block: 'center' });
    });

    this.facCritId$.subscribe(id => {
      this.facCrit$ = this.store.select(AuditState.facCrit(id));

      this.facCrit$.subscribe(facCrit => {
        if (!facCrit) return;

        for (const question of facCrit.questions) {
          this.items.push({ title: question.textDe, data: question.id });
        }
      });
    });
  }
}
