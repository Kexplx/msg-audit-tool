import { AnswerStore } from '../answer.store';
import { AnswerService } from '../../http/answer.service';
import { ANSWERS } from '../../http/test/dummies/app-models/answers';
import { of } from 'rxjs';
import { Answer } from '../../models/answer.model';
import { StoreActionService } from '../store-action.service';

fdescribe('AnswerStore', () => {
  let answerStore: AnswerStore;
  let answerServiceSpy: jasmine.SpyObj<AnswerService>;
  let storeActionServiceSpy: jasmine.SpyObj<StoreActionService>;

  beforeEach(() => {
    answerServiceSpy = jasmine.createSpyObj(['getAnswersByInterviewId', 'putAnswer']);
    answerServiceSpy.getAnswersByInterviewId.and.returnValue(of(ANSWERS));

    storeActionServiceSpy = jasmine.createSpyObj<StoreActionService>(['notifyLoad', 'notifyEdit']);

    answerStore = new AnswerStore(answerServiceSpy, storeActionServiceSpy);
  });

  describe('loadAnswersByInterviewId', () => {
    let answersResponse: Answer[];
    beforeEach(() => {
      answersResponse = ANSWERS;
    });

    it('should call #getAnswersByInterviewId', () => {
      answerStore.loadAnswersByInterviewId(1);

      expect(answerServiceSpy.getAnswersByInterviewId).toHaveBeenCalledWith(1);
      expect(answerServiceSpy.getAnswersByInterviewId.calls.count()).toEqual(1);
    });

    it('should emit the new answers in answers$', () => {
      answerStore.loadAnswersByInterviewId(1);

      answerStore.answers$.subscribe(answers => {
        expect(answers).toEqual(answersResponse);
      });
    });
  });

  describe('updateAnswers', () => {
    let answerResponse: Answer;
    let answers: Answer[];

    beforeEach(() => {
      answerResponse = ANSWERS[0];
      answers = ANSWERS;
      answerServiceSpy.putAnswer.and.returnValue(of(answerResponse));
    });

    it('should call #putAnswer 2 times', () => {
      answerStore['_answers$'].next(answers);
      answerStore.updateAnswers([{} as Answer, {} as Answer]);

      expect(answerServiceSpy.putAnswer.calls.count()).toEqual(2);
    });

    it('should update the answers', () => {
      answerStore['_answers$'].next([{ ...answers[0], reason: 'OLD VALUE' }]);
      answerStore.updateAnswers([{} as Answer]);
      answerStore.answers$.subscribe(answers => {
        expect(answers[0].reason).not.toEqual('OLD VALUE');
      });
    });
  });
});
