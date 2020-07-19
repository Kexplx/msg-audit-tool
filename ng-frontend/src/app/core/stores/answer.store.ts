import { Injectable } from '@angular/core';
import { Answer } from '../data/models/answer.model';
import { AnswerService } from '../http/answer.service';

@Injectable({
  providedIn: 'root',
})
export class AnswerStore {
  answers: Answer[] = [];

  constructor(private answerService: AnswerService) {}

  setAnswers(answers: Answer[]) {
    this.answers = answers;
  }

  updateAnswer(answer: Answer) {
    this.answerService.putAnswer(answer).subscribe(answer => {
      const indexOfAnswer = this.answers.findIndex(
        a =>
          a.faccritId === answer.faccritId &&
          a.interviewId === answer.interviewId &&
          a.questionId === answer.questionId,
      );

      this.answers[indexOfAnswer] = answer;
    });
  }
}
