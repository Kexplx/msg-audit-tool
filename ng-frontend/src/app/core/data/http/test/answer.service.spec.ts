import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { environment } from 'src/environments/environment';
import { AnswerService } from '../answer.service';
import { Answer } from '../../models/answer.model';
import { ANSWERS } from './dummies/app-models/answers';

describe('AnswerService', () => {
  let service: AnswerService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AnswerService],
    });

    service = TestBed.inject(AnswerService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('#getAnswers should return an observable of answers', () => {
    const response: Answer[] = ANSWERS;
    service.getAnswers().subscribe(answers => {
      expect(answers).toEqual(response);
    });

    const req = httpMock.expectOne(environment.baseUrl + 'answers');
    expect(req.request.method).toEqual('GET');

    req.flush(response);
    httpMock.verify();
  });

  it('#getAnswersByInterviewId should return an observable of answers', () => {
    const answerResponse: Answer[] = ANSWERS;
    service.getAnswersByInterviewId(1).subscribe(answers => {
      expect(answers).toEqual(answerResponse);
    });

    const req = httpMock.expectOne(environment.baseUrl + 'answers/interview/1');
    expect(req.request.method).toEqual('GET');

    req.flush(answerResponse);
    httpMock.verify();
  });

  it('#putAnswer should return an answers', () => {
    const response: Answer = ANSWERS[0];
    service.putAnswer({ interviewId: 1, questionId: 2 } as Answer).subscribe(answer => {
      expect(answer).toEqual(response);
    });

    const req = httpMock.expectOne(environment.baseUrl + 'answers/interview/1/question/2');
    expect(req.request.method).toEqual('PUT');

    req.flush(response);
    httpMock.verify();
  });
});
