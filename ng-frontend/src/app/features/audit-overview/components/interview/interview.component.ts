import { Component, Input, OnChanges } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Answer } from 'src/app/core/data/models/answer.model';
import { Question } from 'src/app/core/data/models/question.model';
import { Store, Select } from '@ngxs/store';
import { UpdateAnswer } from 'src/app/core/ngxs/actions/inteview.actions';
import { InterviewState } from 'src/app/core/ngxs/interview.state';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-interview',
  templateUrl: './interview.component.html',
  styleUrls: ['./interview.component.scss'],
})
export class InterviewComponent implements OnChanges {
  @Input() answers: Answer[];
  @Select(InterviewState.questions) questions$: Observable<Question[]>;

  formGroups: FormGroup[];

  constructor(public store: Store, private fb: FormBuilder) {}

  ngOnChanges() {
    this.formGroups = [];

    for (const answer of this.answers) {
      if (this.formGroups)
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

      this.store.dispatch(new UpdateAnswer(answer));
    }
  }
}
