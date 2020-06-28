import { TestBed } from '@angular/core/testing';
import { NgxsModule, Store } from '@ngxs/store';
import { Answer } from '../data/models/answer.model';
import { generate } from 'shortid';
import { AnswerState } from './answer.state';
import { AddAnswer, UpdateAnswer } from './actions/answer.actions';

describe('AnswerState', () => {
  let answer: Answer;
  let store: Store;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [NgxsModule.forRoot([AnswerState])],
    });

    store = TestBed.inject(Store);
    answer = {
      facCritId: generate(),
      interviewId: generate(),
      questionId: generate(),
    };
  });

  it('should add an answer when addAnswer is dispatched', () => {
    const length = store.selectSnapshot(AnswerState.answers)?.length ?? 0;
    store.dispatch(new AddAnswer(answer));
    const lengthNow = store.selectSnapshot(AnswerState.answers)?.length ?? 0;
    expect(lengthNow).toEqual(length + 1);
  });

  it('should update an answer when updateAnswer is dispatched', () => {
    store.dispatch(new AddAnswer(answer));
    const updatedAnswer: Answer = { ...answer, reason: 'UPDATE' };

    store.dispatch(new UpdateAnswer(updatedAnswer));

    const reason = store.selectSnapshot(
      AnswerState.answer(answer.facCritId, answer.interviewId, answer.questionId),
    ).reason;

    expect(reason).toEqual('UPDATE');
  });

  it('it should get a answer by id when valid ids are passed', () => {
    store.dispatch(new AddAnswer(answer));
    const cp = AnswerState.answer(answer.facCritId, answer.interviewId, answer.questionId);
    expect(cp).toBeTruthy();
  });
});
