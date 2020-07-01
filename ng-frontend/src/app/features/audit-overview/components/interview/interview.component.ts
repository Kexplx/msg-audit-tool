import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Interview } from 'src/app/core/data/models/interview.model';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';
import { Select, Store } from '@ngxs/store';
import { AuditState } from 'src/app/core/ngxs/audit.state';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AnswerState } from 'src/app/core/ngxs/answer.state';
import { Answer } from 'src/app/core/data/models/answer.model';
import { AddAnswer, UpdateAnswer } from 'src/app/core/ngxs/actions/answer.actions';
import { AppRouterState } from 'src/app/core/ngxs/app-router.state';
import { InterviewState } from 'src/app/core/ngxs/interview.state';
import { UpdateInterview } from 'src/app/core/ngxs/actions/inteview.actions';

@Component({
  selector: 'app-interview',
  templateUrl: './interview.component.html',
  styleUrls: ['./interview.component.scss'],
})
export class InterviewComponent implements OnInit {
  @Select(AuditState.facCrits) facCrits$: Observable<FacCrit[]>;

  @Select(AppRouterState.auditId) auditId$: Observable<number>;
  @Select(AppRouterState.interviewId) interviewId$: Observable<number>;
  @Select(AppRouterState.facCritId) facCritId$: Observable<number>;

  auditId: number;
  interviewId: number;
  facCritId: number;

  interview$: Observable<Interview>;
  facCrit$: Observable<FacCrit>;

  interviewGoal: string;
  formGroups: FormGroup[];

  constructor(private store: Store, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.interviewId$.subscribe(id => {
      this.interviewId = id;
      this.interview$ = this.store.select(InterviewState.interview(id));

      this.interview$.subscribe(interview => {
        this.interviewGoal = interview?.goal;
      });
    });

    this.facCritId$.subscribe(id => {
      this.facCritId = id;
      this.facCrit$ = this.store.select(AuditState.facCrit(id));
    });

    this.auditId$.subscribe(id => (this.auditId = id));

    this.facCrit$.subscribe(facCrit => {
      this.formGroups = [];
      for (const question of facCrit.questions) {
        this.store
          .select(AnswerState.answer(this.facCritId, this.interviewId, question.id))
          .subscribe(answer => {
            this.formGroups.push(
              this.fb.group({
                result: [answer?.result],
                responsible: [answer?.responsible],
                documentation: [answer?.documentation],
                procedure: [answer?.procedure],
                reason: [answer?.reason],
                proof: [answer?.proof],
                annotation: [answer?.annotation],
              }),
            );
          });
      }
    });
  }

  onSave() {
    this.facCrit$.subscribe(facCrit => {
      for (const [i, question] of facCrit.questions.entries()) {
        const formGroup = this.formGroups[i];

        const answer: Answer = {
          proof: formGroup.get('proof').value,
          result: formGroup.get('result').value,
          documentation: formGroup.get('documentation').value,
          procedure: formGroup.get('procedure').value,
          reason: formGroup.get('reason').value,
          annotation: formGroup.get('annotation').value,
          responsible: formGroup.get('responsible').value,
          faccritId: this.facCritId,
          interviewId: this.interviewId,
          questionId: question.id,
        };

        this.store.selectSnapshot(AnswerState.answer(this.facCritId, this.interviewId, question.id))
          ? this.store.dispatch(new UpdateAnswer(answer))
          : this.store.dispatch(new AddAnswer(answer));
      }
    });

    const interviewSnapshot = this.store.selectSnapshot(InterviewState.interview(this.interviewId));
    this.store.dispatch(
      new UpdateInterview(this.interviewId, { ...interviewSnapshot, goal: this.interviewGoal }),
    );
  }
}
