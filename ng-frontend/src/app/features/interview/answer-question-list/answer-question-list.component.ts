import { Component, Input, OnChanges, OnDestroy } from '@angular/core';
import { Answer } from 'src/app/core/data/models/answer.model';
import { FormGroup, FormBuilder } from '@angular/forms';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';
import { Audit } from 'src/app/core/data/models/audit.model';
import { SubSink } from 'subsink';
import { AnswerStore } from 'src/app/core/data/stores/answer.store';
import { map, filter } from 'rxjs/operators';

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

  formGroups: FormGroup[] = [];

  private readonly subSink = new SubSink();

  constructor(private fb: FormBuilder, private answerStore: AnswerStore) {}

  ngOnChanges() {
    const answersSub = this.answerStore.answers$
      .pipe(
        filter(answers => answers != null),
        map(answers => answers.filter(a => a.faccritId === this.facCrit.id)),
      )
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
      });

    this.subSink.add(answersSub);
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

      this.answerStore.updateAnswer(answer);
    }
  }
}
