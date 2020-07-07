import { Component } from '@angular/core';
import { Observable, forkJoin, combineLatest } from 'rxjs';
import { Select } from '@ngxs/store';
import { NbMenuService, NbMenuItem } from '@nebular/theme';
import { Question } from 'src/app/core/data/models/question.model';
import { InterviewService } from 'src/app/core/http/interview.service';
import { AppRouterState } from 'src/app/core/ngxs/app-router.state';
import { InterviewState } from 'src/app/core/ngxs/interview.state';
import { Answer } from 'src/app/core/data/models/answer.model';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-sidebar-interview',
  templateUrl: './sidebar-interview.component.html',
  styleUrls: ['./sidebar-interview.component.scss'],
})
export class SidebarInterviewComponent {
  @Select(AppRouterState.facCritId) facCritId$: Observable<number>;
  @Select(AppRouterState.interviewId) interviewId$: Observable<number>;
  @Select(InterviewState.answers) answers$: Observable<Answer[]>;

  items: NbMenuItem[];

  constructor(private menuService: NbMenuService, private interviewService: InterviewService) {}
  ngOnInit() {
    this.items = [];
    this.menuService.onItemClick().subscribe(x => {
      const el = document.getElementById(x.item.data);
      el?.scrollIntoView({ behavior: 'smooth', block: 'center' });
    });

    combineLatest([this.facCritId$, this.interviewId$]).subscribe(ids => {
      this.answers$
        .pipe(map(answers => answers.filter(a => a.faccritId === ids[0])))
        .subscribe(answers => {
          const questionObservables: Observable<Question>[] = [];
          for (const answer of answers) {
            questionObservables.push(this.interviewService.getQuestion(answer.questionId));

            forkJoin(questionObservables).subscribe(questions => {
              this.items = questions.map<NbMenuItem>(q => {
                return { title: q.textDe, data: q.id };
              });
            });
          }
        });
    });
  }
}
