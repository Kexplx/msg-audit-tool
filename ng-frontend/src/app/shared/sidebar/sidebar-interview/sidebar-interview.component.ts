import { Component, OnInit, Input } from '@angular/core';
import { Observable } from 'rxjs';
import { Store } from '@ngxs/store';
import { NbMenuService, NbMenuItem } from '@nebular/theme';
import { AuditState } from 'src/app/core/ngxs/audit.state';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';

@Component({
  selector: 'app-sidebar-interview',
  templateUrl: './sidebar-interview.component.html',
  styleUrls: ['./sidebar-interview.component.scss'],
})
export class SidebarInterviewComponent implements OnInit {
  @Input() url: string;
  facCrit$: Observable<FacCrit>;

  constructor(private store: Store, private menuService: NbMenuService) {}

  items: NbMenuItem[];

  ngOnInit() {
    this.menuService.onItemClick().subscribe(x => {
      const el = document.getElementById(x.item.data);
      el?.scrollIntoView({ behavior: 'smooth', block: 'center' });
    });

    const id = this.url.split('/').slice(-1)[0];

    this.facCrit$ = this.store.select(AuditState.facCrit(id));

    this.items = [];
    this.facCrit$.subscribe(facCrit => {
      if (!facCrit) return;

      for (const question of facCrit.questions) {
        this.items.push({ title: question.textDe, data: question.id });
      }
    });
  }
}
