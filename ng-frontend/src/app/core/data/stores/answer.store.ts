import { Injectable } from '@angular/core';
import { Answer } from '../models/answer.model';
import { AnswerService } from '../http/answer.service';
import { BehaviorSubject, Observable, forkJoin } from 'rxjs';
import { StoreActionService } from './store-action.service';

@Injectable({
  providedIn: 'root',
})
export class AnswerStore {
  private readonly _answers$ = new BehaviorSubject<Answer[]>(null);

  get answers$() {
    return this._answers$.asObservable();
  }

  constructor(
    private answerService: AnswerService,
    private storeActionService: StoreActionService,
  ) {}

  loadAnswersByInterviewId(id: number) {
    this.answerService
      .getAnswersByInterviewId(id)
      .subscribe(answers => this._answers$.next(answers));
  }

  updateAnswers(answers: Answer[]) {
    const answers$: Observable<Answer>[] = [];
    for (const answer of answers) {
      answers$.push(this.answerService.putAnswer(answer));
    }

    forkJoin(answers$).subscribe(answers => {
      for (const answer of answers) {
        this.updateAnswer(answer);
      }

      this.storeActionService.notifyEdit('Antworten wurden aktualisiert.');
    });
  }

  private updateAnswer(answer: Answer): void {
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
  }
}
