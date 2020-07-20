import { Component } from '@angular/core';
import { NbMenuService, NbMenuItem } from '@nebular/theme';
import { map, filter } from 'rxjs/operators';
import { AnswerStore } from '../../stores/answer.store';
import { IdService } from '../../id.service';

@Component({
  selector: 'app-sidebar-interview',
  templateUrl: './sidebar-interview.component.html',
  styleUrls: ['./sidebar-interview.component.scss'],
})
export class SidebarInterviewComponent {
  items: NbMenuItem[];
  constructor(
    private menuService: NbMenuService,
    private answerStore: AnswerStore,
    private routesService: IdService,
  ) {}

  ngOnInit() {
    this.items = [];
    this.menuService.onItemClick().subscribe(x => {
      const el = document.getElementById(x.item.data);
      el?.scrollIntoView({ behavior: 'smooth', block: 'center' });
    });

    this.routesService.facCritId$.subscribe(id => {
      this.answerStore.answers$
        .pipe(
          filter(answers => answers != null),
          map(answers => answers.filter(a => a.faccritId === id)),
        )
        .subscribe(answers => {
          this.items = answers.map<NbMenuItem>(a => ({
            title: a.questionText,
            data: a.questionId,
          }));
        });
    });
  }
}
