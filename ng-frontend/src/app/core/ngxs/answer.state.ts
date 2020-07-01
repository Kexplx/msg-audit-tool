import { State, Selector, createSelector, Action, StateContext } from '@ngxs/store';
import { Injectable } from '@angular/core';
import { Answer } from '../data/models/answer.model';
import { AddAnswer, UpdateAnswer } from './actions/answer.actions';
import { append, patch, updateItem } from '@ngxs/store/operators';

export interface AnswerStateModel {
  answers: Answer[];
}

/**
 * State for managing the answers of the application
 *
 * Action handlers to add and update an answer
 * Static and dynamic selectors to select answers
 */
@State<AnswerState>({
  name: 'answerState',
})
@Injectable()
export class AnswerState {
  @Selector()
  static answers(state: AnswerStateModel) {
    return state.answers;
  }

  static answer(facCritId: number, interviewId: number, questionId: number) {
    return createSelector([AnswerState], (state: AnswerStateModel) => {
      return state.answers.find(
        x =>
          x.faccritId === facCritId && x.questionId === questionId && x.interviewId && interviewId,
      );
    });
  }

  @Action(AddAnswer)
  addAnswer({ setState }: StateContext<AnswerStateModel>, { answer }: AddAnswer) {
    setState(
      patch({
        answers: append<Answer>([answer]),
      }),
    );
  }

  @Action(UpdateAnswer)
  updateAnswer({ setState }: StateContext<AnswerStateModel>, { answer }: AddAnswer) {
    setState(
      patch({
        answers: updateItem<Answer>(
          x =>
            x.faccritId === answer.faccritId &&
            x.interviewId === answer.interviewId &&
            x.questionId === answer.questionId,
          answer,
        ),
      }),
    );
  }
}
