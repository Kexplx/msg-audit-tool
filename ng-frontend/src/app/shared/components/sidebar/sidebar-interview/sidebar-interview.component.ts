import { Component } from '@angular/core';
import { Observable, combineLatest } from 'rxjs';
import { Select, Store } from '@ngxs/store';
import { NbMenuService, NbMenuItem } from '@nebular/theme';
import { Question } from 'src/app/core/data/models/question.model';
import { AppRouterState } from 'src/app/core/ngxs/app-router.state';
import { InterviewState } from 'src/app/core/ngxs/interview.state';
import { Answer } from 'src/app/core/data/models/answer.model';
import { map, first } from 'rxjs/operators';

@Component({
  selector: 'app-sidebar-interview',
  templateUrl: './sidebar-interview.component.html',
  styleUrls: ['./sidebar-interview.component.scss'],
})
export class SidebarInterviewComponent {
  @Select(AppRouterState.facCritId) facCritId$: Observable<number>;
  @Select(AppRouterState.interviewId) interviewId$: Observable<number>;
  @Select(InterviewState.answers) answers$: Observable<Answer[]>;
  @Select(InterviewState.questions) questions$: Observable<Question[]>;

  items: NbMenuItem[];

  questionIds: number[];

  constructor(private menuService: NbMenuService) {}
  
  ngOnInit() {
    this.items = [];
    this.menuService.onItemClick().subscribe(x => {
      const el = document.getElementById(x.item.data);
      el?.scrollIntoView({ behavior: 'smooth', block: 'center' });
    });

    combineLatest([this.facCritId$, this.interviewId$]).subscribe(ids => {
      this.answers$
        .pipe(
          first(answers => answers.length > 0),
          map(answers => answers.filter(a => a.faccritId === ids[0] && a.interviewId === ids[1])),
        )
        .subscribe(answers => {
          this.questionIds = answers.map(a => a.questionId);
        });

      this.questions$.pipe(first(questions => questions.length > 0)).subscribe(questions => {
        if (this.questionIds) {
          this.items = questions
            .filter(q => this.questionIds.includes(q.id) && q.facCritId === ids[0])
            .sort((a, b) => a.id - b.id)
            .map<NbMenuItem>(q => ({ title: q.textDe, data: q.id }));
        }
      });
    });
  }
}
