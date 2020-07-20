import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { environment } from 'src/environments/environment';
import { QuestionService } from '../question.service';
import { Question } from '../../models/question.model';
import { QUESTIONS } from './dummies/app-models/questions';

describe('QuestionService', () => {
  let service: QuestionService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [QuestionService],
    });

    service = TestBed.inject(QuestionService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('#getQuestion should return an observable of a question', () => {
    const questionDto: Question = QUESTIONS[0];
    service.getQuestion(1).subscribe(question => {
      expect(question).toEqual(questionDto);
    });

    const req = httpMock.expectOne(environment.baseUrl + 'questions/1');
    expect(req.request.method).toEqual('GET');

    req.flush(questionDto);
    httpMock.verify();
  });
});
