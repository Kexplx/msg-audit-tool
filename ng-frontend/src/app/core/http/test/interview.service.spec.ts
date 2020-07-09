import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { compileTimeSwitchedString } from '../connectionStrings';
import * as karma from 'karma-jasmine';
import { InterviewService } from '../interview.service';
import { InterviewDto } from '../dtos/interview.dto';
import { INTERVIEWS_DTO_DUMMY } from './dummies/interviews';
import { Interview } from '../../data/models/interview.model';

describe('InterviewService', () => {
  let service: InterviewService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [InterviewService],
    });

    service = TestBed.inject(InterviewService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('#getInterview should return an observable of an interview', () => {
    const interviewDto: InterviewDto = INTERVIEWS_DTO_DUMMY[0];

    service.getInterview(1).subscribe(interview => {
      verifyInterviewContent(interview, interviewDto);
    });

    const req = httpMock.expectOne(compileTimeSwitchedString + 'interviews/' + 1);
    expect(req.request.method).toEqual('GET');

    req.flush(interviewDto);

    httpMock.verify();
  });

  function verifyInterviewContent(interview: Interview, interviewDto: InterviewDto) {
    const { id, status, startDate, contactPersons, answers, auditId, goal } = interview;
    expect(id).toEqual(interviewDto.id);
    expect(startDate).toEqual(new Date(interviewDto.startDate).getTime());
    expect(status).toEqual(interviewDto.status);
    expect(contactPersons).toEqual(interviewDto.interviewedContactPersons);
    expect(answers).toEqual(interviewDto.answers);
    expect(auditId).toEqual(interviewDto.auditId);
    expect(goal).toEqual(interviewDto.goal);
  }
});
