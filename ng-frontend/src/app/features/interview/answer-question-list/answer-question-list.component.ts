import { Component, Input, OnChanges, OnDestroy } from '@angular/core';
import { Answer } from 'src/app/core/data/models/answer.model';
import { Store } from '@ngxs/store';
import { Observable, combineLatest } from 'rxjs';
import { Question } from 'src/app/core/data/models/question.model';
import { FormGroup, FormBuilder } from '@angular/forms';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';
import { Audit } from 'src/app/core/data/models/audit.model';
import { QuestionService } from 'src/app/core/http/question.service';
import { AnswerService } from 'src/app/core/http/answer.service';
import { map } from 'rxjs/operators';
import { SubSink } from 'subsink';

@Component({
  selector: 'app-answer-question-list',
  templateUrl: './answer-question-list.component.html',
  styleUrls: ['./answer-question-list.component.scss'],
})
export class AnswerQuestionListComponent implements OnChanges, OnDestroy {
  @Input() facCrit: FacCrit;
  @Input() audit: Audit;
  @Input() interviewId: number;

  answers: Answer[];
  questions: Question[];

  formGroups: FormGroup[] = [];

  private readonly subSink = new SubSink();

  constructor(
    public store: Store,
    private fb: FormBuilder,
    private questionService: QuestionService,
    private answerService: AnswerService,
  ) {}

  ngOnChanges() {
    const answerSub = this.answerService
      .getAnswersByInterviewId(this.interviewId)
      .pipe(map(answers => answers.filter(a => a.faccritId === this.facCrit.id)))
      .subscribe(answers => {
        this.answers = answers;
        for (const [i, answer] of this.answers.entries()) {
          this.formGroups[i] = this.fb.group({
            result: [answer?.result],
            responsible: [answer?.responsible],
            documentation: [answer?.documentation],
            procedure: [answer?.procedure],
            reason: [answer?.reason],
            proof: [answer?.proof],
            annotation: [answer?.annotation],
          });
        }

        // Here we get each question by it's id that is contained in the answers
        const questionObservable$: Observable<Question>[] = [];
        const questionIds = answers.map(a => a.questionId);
        for (const id of questionIds) {
          questionObservable$.push(this.questionService.getQuestion(id));
        }

        const questionSub = combineLatest(questionObservable$).subscribe(questions => {
          this.questions = questions;
        });

        this.subSink.add(questionSub);
      });

    this.subSink.add(answerSub);
  }

  ngOnDestroy() {
    this.subSink.unsubscribe();
  }

  onSave() {
    for (const [i, a] of this.answers.entries()) {
      const formGroup = this.formGroups[i];
      const answer: Answer = {
        ...a,
        proof: formGroup.get('proof').value,
        result: formGroup.get('result').value,
        documentation: formGroup.get('documentation').value,
        procedure: formGroup.get('procedure').value,
        reason: formGroup.get('reason').value,
        annotation: formGroup.get('annotation').value,
        responsible: formGroup.get('responsible').value,
      };

      this.answerService.putAnswer(answer).subscribe(() => {});
    }
  }
}
