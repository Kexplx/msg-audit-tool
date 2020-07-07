import { Component } from '@angular/core';
import { Observable, forkJoin, combineLatest } from 'rxjs';
import { Select, Store } from '@ngxs/store';
import { NbMenuService, NbMenuItem } from '@nebular/theme';
import { Question } from 'src/app/core/data/models/question.model';
import { InterviewService } from 'src/app/core/http/interview.service';
import { AppRouterState } from 'src/app/core/ngxs/app-router.state';
import { InterviewState } from 'src/app/core/ngxs/interview.state';
import { Answer } from 'src/app/core/data/models/answer.model';
import { map, filter } from 'rxjs/operators';
import { LoadQuestion } from 'src/app/core/ngxs/actions/inteview.actions';

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

  constructor(private menuService: NbMenuService, private store: Store) {}
  ngOnInit() {
    this.items = [];
    this.menuService.onItemClick().subscribe(x => {
      const el = document.getElementById(x.item.data);
      el?.scrollIntoView({ behavior: 'smooth', block: 'center' });
    });

    combineLatest([this.facCritId$, this.interviewId$]).subscribe(ids => {
      this.answers$
        .pipe(
          map(answers =>
            answers.filter(a => a.faccritId === ids[0] && a.interviewId === a.interviewId),
          ),
          filter(answers => answers.length >= 7),
        )
        .subscribe(answers => {
          this.questionIds = answers.map(a => a.questionId);
        });

      this.questions$.subscribe(questions => {
        if (this.questionIds) {
          this.items = questions
            .filter(q => this.questionIds.includes(q.id))
            .map<NbMenuItem>(q => ({ title: q.textDe, data: q.id }));
        }
      });
    });
  }
}
