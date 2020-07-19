import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Select } from '@ngxs/store';
import { NbMenuService, NbMenuItem } from '@nebular/theme';
import { AppRouterState } from 'src/app/core/ngxs/app-router.state';
import { map, filter } from 'rxjs/operators';
import { AnswerStore } from '../../stores/answer.store';

@Component({
  selector: 'app-sidebar-interview',
  templateUrl: './sidebar-interview.component.html',
  styleUrls: ['./sidebar-interview.component.scss'],
})
export class SidebarInterviewComponent {
  @Select(AppRouterState.facCritId) facCritId$: Observable<number>;
  @Select(AppRouterState.interviewId) interviewId$: Observable<number>;

  items: NbMenuItem[];

  questionIds: number[];

  constructor(private menuService: NbMenuService, private answerStore: AnswerStore) {}

  ngOnInit() {
    this.items = [];
    this.menuService.onItemClick().subscribe(x => {
      const el = document.getElementById(x.item.data);
      el?.scrollIntoView({ behavior: 'smooth', block: 'center' });
    });

    this.facCritId$.subscribe(id => {
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
