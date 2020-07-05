import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Answer } from 'src/app/core/data/models/answer.model';
import { Question } from 'src/app/core/data/models/question.model';
import { Router } from '@angular/router';
import { InterviewService } from 'src/app/core/http/interview.service';
import { Observable, forkJoin, combineLatest } from 'rxjs';
import { Store, Select } from '@ngxs/store';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';
import { AppRouterState } from 'src/app/core/ngxs/app-router.state';

@Component({
  selector: 'app-interview',
  templateUrl: './interview.component.html',
  styleUrls: ['./interview.component.scss'],
})
export class InterviewComponent implements OnInit {
  @Select(AppRouterState.interviewId) interviewId$: Observable<number>;
  @Select(AppRouterState.facCritId) facCritId$: Observable<number>;
  facCrit$: Observable<FacCrit>;

  allLoaded = false;
  interviewId: number;
  facCritId: number;
  answers: Answer[];
  questions: Question[] = [];
  formGroups: FormGroup[];

  constructor(private interviewservice: InterviewService, private fb: FormBuilder) {}

  ngOnInit() {
    console.log('test');

    combineLatest(this.interviewId$, this.facCritId$).subscribe(ids => {
      this.allLoaded = false;
      this.formGroups = [];
      this.interviewId = ids[0];
      this.facCritId = ids[1];
      console.log(this.interviewId, this.facCritId);

      this.interviewservice.getAnswersByInterviewId(this.interviewId).subscribe(answers => {
        this.answers = answers.filter(a => a.faccritId === this.facCritId);
        console.log(this.answers);

        const questionObservables: Observable<Question>[] = [];
        for (const answer of this.answers) {
          questionObservables.push(this.interviewservice.getQuestion(answer.questionId));

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
        }

        forkJoin(questionObservables).subscribe(questions => {
          this.questions = questions;
          this.allLoaded = true;
        });
      });
    });
  }

  questionById(id: number) {
    return this.questions.find(q => q.id === id);
  }

  onSave() {
    for (const [i, a] of this.answers.entries()) {
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
        questionId: a.questionId,
      };

      this.interviewservice.putAnswer(answer).subscribe(() => {});
    }
  }
}
