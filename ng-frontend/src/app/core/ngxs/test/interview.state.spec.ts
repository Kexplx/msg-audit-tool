import { TestBed } from '@angular/core/testing';
import { Store, NgxsModule } from '@ngxs/store';
import { of } from 'rxjs';
import { HttpClientModule } from '@angular/common/http';
import { QuestionService } from '../../http/question.service';
import { AnswerService } from '../../http/answer.service';
import { InterviewState } from '../interview.state';
import { InterviewService } from '../../http/interview.service';
import { QUESTIONS_DTO_DUMMY } from '../../http/test/dummies/questions';
import { ANSWERS_DTO_DUMMY } from '../../http/test/dummies/answers';
import { Answer } from '../../data/models/answer.model';
import { INTERVIEWS_DUMMY } from './dummies/interviews';
import { AddInterview, UpdateInterview, UpdateAnswer } from '../actions/inteview.actions';
import { Interview } from '../../data/models/interview.model';
import { FacCrit } from '../../data/models/faccrit.model';

describe('ContactPersonState', () => {
  let store: Store;
  let interviewServiceSpy: jasmine.SpyObj<InterviewService>;
  let questionServiceSpy: jasmine.SpyObj<QuestionService>;
  let answerServiceSpy: jasmine.SpyObj<AnswerService>;

  beforeEach(() => {
    questionServiceSpy = jasmine.createSpyObj('QuestionService', ['getQuestion']);
    interviewServiceSpy = jasmine.createSpyObj('InterviewService', [
      'getInterview',
      'getInterviews',
      'postInterview',
      'putInterview',
      'getInterviewsByAuditId',
    ]);
    answerServiceSpy = jasmine.createSpyObj('AnswerService', [
      'getAnswers',
      'getAnswersByInterviewId',
      'putAnswer',
    ]);

    interviewServiceSpy.getInterviews.and.returnValue(of(INTERVIEWS_DUMMY));
    interviewServiceSpy.getInterviewsByAuditId.and.returnValue(of(INTERVIEWS_DUMMY));
    interviewServiceSpy.getInterview.and.returnValue(of(INTERVIEWS_DUMMY[0]));
    interviewServiceSpy.postInterview.and.returnValue(of(INTERVIEWS_DUMMY[0]));
    interviewServiceSpy.putInterview.and.returnValue(of(INTERVIEWS_DUMMY[0]));

    questionServiceSpy.getQuestion.and.returnValue(of(QUESTIONS_DTO_DUMMY[0]));

    answerServiceSpy.getAnswers.and.returnValue(of(ANSWERS_DTO_DUMMY));
    answerServiceSpy.getAnswersByInterviewId.and.returnValue(of(ANSWERS_DTO_DUMMY));
    answerServiceSpy.putAnswer.and.returnValue(of(ANSWERS_DTO_DUMMY[0]));

    TestBed.configureTestingModule({
      imports: [NgxsModule.forRoot([InterviewState]), HttpClientModule],
      providers: [
        { provide: InterviewService, useValue: interviewServiceSpy },
        { provide: AnswerService, useValue: answerServiceSpy },
        { provide: QuestionService, useValue: questionServiceSpy },
      ],
    });

    store = TestBed.inject(Store);
  });

  it('#ngxsOnInit calls #getInterviews once and for each question #getQuestion and fills the state with the results', () => {
    // ngxsOnInit() called by framework...
    const interviews = store.selectSnapshot(InterviewState.interviews);
    const answers = store.selectSnapshot(InterviewState.answers);
    const questions = store.selectSnapshot(InterviewState.questions);

    const answersInInterviews: Answer[] = [].concat.apply(
      [],
      INTERVIEWS_DUMMY.map(i => i.answers),
    );
    const questionsInInterviews = new Array(answersInInterviews.length).fill(
      QUESTIONS_DTO_DUMMY[0],
    );

    expect(interviews).toEqual(INTERVIEWS_DUMMY);
    expect(answers).toEqual(answersInInterviews);
    expect(questions).toEqual(questionsInInterviews);

    expect(interviewServiceSpy.getInterviews.calls.count()).toBe(1);
    expect(questionServiceSpy.getQuestion.calls.count()).toBe(questionsInInterviews.length);
  });

  it('#question returns a question by id', () => {
    const questions = store.selectSnapshot(InterviewState.questions);
    const contactPerson = store.selectSnapshot(InterviewState.question(questions[0].id));

    expect(questions[0]).toEqual(contactPerson);
  });

  it('#interview returns an interview by id', () => {
    const interviews = store.selectSnapshot(InterviewState.interviews);
    const interview = store.selectSnapshot(InterviewState.interview(interviews[0].id));

    expect(interviews[0]).toEqual(interview);
  });

  it('#interviewsByAuditId returns all interviews by their auditId', () => {
    const interviewsManually = store
      .selectSnapshot(InterviewState.interviews)
      .filter(i => i.auditId === 123456);
    const interviewsFromState = store.selectSnapshot(InterviewState.interviewsByAuditId(123456));

    expect(interviewsManually).toEqual(interviewsFromState);
  });

  it('#addInterview calls #postInterview once and fills the state with the results', () => {
    const interviewsOld = store.selectSnapshot(InterviewState.interviews);
    store.dispatch(new AddInterview({} as Interview, [] as FacCrit[]));
    const interviewsNew = store.selectSnapshot(InterviewState.interviews);

    expect(interviewsNew.length - interviewsOld.length).toEqual(1);
    expect(interviewsNew.slice(-1)[0]).toEqual(INTERVIEWS_DUMMY[0]);

    expect(interviewServiceSpy.postInterview.calls.count()).toEqual(1);
  });

  it('#updateInterview calls #postInterview once and updates the interview in the state', () => {
    const interviewsOld = store.selectSnapshot(InterviewState.interviews);
    store.dispatch(new UpdateInterview(interviewsOld[0].id, {} as Interview));
    const interviewsNew = store.selectSnapshot(InterviewState.interviews);

    expect(interviewsNew.length - interviewsOld.length).toEqual(0);
    expect(interviewsNew[0]).toEqual(INTERVIEWS_DUMMY[0]);
    expect(interviewServiceSpy.putInterview.calls.count()).toEqual(1);
  });

  it('#updateAnswer calls #putAnswer once and updates the answer in the state', () => {
    const answersOld = store.selectSnapshot(InterviewState.answers);
    store.dispatch(new UpdateAnswer(answersOld[1]));
    const answersNew = store.selectSnapshot(InterviewState.answers);

    expect(answersNew.length - answersOld.length).toEqual(0);
    expect(answersNew[0]).toEqual(answersNew[0]);

    expect(answerServiceSpy.putAnswer.calls.count()).toEqual(1);
  });
});
