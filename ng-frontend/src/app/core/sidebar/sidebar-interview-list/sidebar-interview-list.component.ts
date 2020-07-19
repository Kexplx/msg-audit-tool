import { Component, OnInit } from '@angular/core';
import { Store, Select } from '@ngxs/store';
import { Observable, timer } from 'rxjs';
import { Audit } from 'src/app/core/data/models/audit.model';
import { NbMenuItem, NbMenuService } from '@nebular/theme';
import { AppRouterState } from 'src/app/core/ngxs/app-router.state';
import { filter, map, first } from 'rxjs/operators';
import { AuditStore } from '../../stores/audit.store';
@Component({
  selector: 'app-sidebar-interview-list',
  templateUrl: './sidebar-interview-list.component.html',
  styleUrls: ['./sidebar-interview-list.component.scss'],
})
export class SidebarInterviewListComponent implements OnInit {
  @Select(AppRouterState.auditId) auditId$: Observable<number>;

  audit$: Observable<Audit>;
  items: NbMenuItem[];

  constructor(private auditStore: AuditStore, private menuService: NbMenuService) {}

  ngOnInit() {
    this.menuService.onItemClick().subscribe(x => {
      const el = document.getElementById(x.item.data);
      el?.scrollIntoView({ behavior: 'smooth', block: 'center' });
    });

    this.auditId$.subscribe(id => {
      this.auditStore.audits$
        .pipe(
          filter(audits => audits != null),
          first(),
          map(audits => audits.find(a => a.id === id)),
        )
        .subscribe(audit => {
          this.items = [];
          for (const factor of audit.scope.filter(fc => !fc.referenceId)) {
            const criterias = audit.scope.filter(
              fc => fc.referenceId && fc.referenceId === factor.id,
            );

            const menuItem: NbMenuItem = {
              title: factor.name,
              data: factor.id,
            };

            if (criterias.length > 0) {
              menuItem.children = criterias.map(c => {
                return { title: c.name, data: c.id };
              });
            }

            this.items.push(menuItem);
            timer(0).subscribe(() => this.cropMenuItemTitles());
          }
        });
    });
  }

  /**
   * Crops all titles of nebular's menu to fit onto one line.
   */
  private cropMenuItemTitles() {
    const spans: NodeListOf<HTMLSpanElement> = document.querySelectorAll('nb-menu .menu-title');
    spans.forEach(s => {
      s.innerText = this.cropString(s.innerText, 25);
    });
  }

  /**
   * Crops a string after n characters and appens "..." onto it.
   *
   * @example
   * cropString('Hello World', 5);
   * // Will return "Hello..."
   *
   *
   * @param s The string to crop.
   * @param n The length after which to crop.
   */
  private cropString(s: string, n: number): string {
    if (s.length < n) return s;

    return s.substr(0, n).trim() + '...';
  }
}
