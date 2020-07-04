import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Answer } from 'src/app/core/data/models/answer.model';
import { Question } from 'src/app/core/data/models/question.model';
import { Router } from '@angular/router';
import { InterviewService } from 'src/app/core/http/interview.service';
import { Observable, forkJoin } from 'rxjs';
import { Store } from '@ngxs/store';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';
import { AuditState } from 'src/app/core/ngxs/audit.state';

@Component({
  selector: 'app-interview',
  templateUrl: './interview.component.html',
  styleUrls: ['./interview.component.scss'],
})
export class InterviewComponent implements OnInit {
  facCrit$: Observable<FacCrit>;

  allLoaded = false;
  interviewId: number;
  facCritId: number;
  answers: Answer[];
  questions: Question[] = [];
  formGroups: FormGroup[];

  constructor(
    private store: Store,
    private interviewservice: InterviewService,
    private fb: FormBuilder,
    private router: Router,
  ) {}

  ngOnInit() {
    const interviewRegex = /^\/audits\/([^\/]*)\/interviews\/([^\/]*)\/([^\/])*$/;
    const regexResult = interviewRegex.exec(this.router.url);
    this.interviewId = +regexResult[2];
    this.facCritId = +regexResult[3];

    this.facCrit$ = this.store.select(AuditState.facCrit(this.facCritId));
    this.formGroups = [];

    this.interviewservice.getAnswersByInterviewId(this.interviewId).subscribe(answers => {
      this.answers = answers.filter(a => a.faccritId === this.facCritId);

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
