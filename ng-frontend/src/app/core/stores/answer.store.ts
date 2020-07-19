import { Injectable } from '@angular/core';
import { Answer } from '../data/models/answer.model';
import { AnswerService } from '../http/answer.service';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AnswerStore {
  private readonly _answers$ = new BehaviorSubject<Answer[]>(null);

  get answers$() {
    return this._answers$.asObservable();
  }

  constructor(private answerService: AnswerService) {}

  loadAnswersByInterviewId(id: number) {
    this.answerService
      .getAnswersByInterviewId(id)
      .subscribe(answers => this._answers$.next(answers));
  }

  updateAnswer(answer: Answer) {
    this.answerService.putAnswer(answer).subscribe(answer => {
      const answers = this._answers$.value;
      const indexOfAnswer = answers.findIndex(
        a =>
          a.faccritId === answer.faccritId &&
          a.interviewId === answer.interviewId &&
          a.questionId === answer.questionId,
      );

      this._answers$.next([
        ...answers.slice(0, indexOfAnswer),
        answer,
        ...answers.slice(indexOfAnswer + 1),
      ]);
    });
  }
}
