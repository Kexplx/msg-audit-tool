import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { InterviewService } from '../interview.service';
import { InterviewResponse } from '../response-models/interview.response';
import { INTERVIEWS_RESPONSE } from './dummies/responses/interviews-response';
import { Interview } from '../../models/interview.model';
import { environment } from 'src/environments/environment';
import { FACCRITS } from './dummies/app-models/faccrits';
import { INTERVIEWS } from './dummies/app-models/interviews';

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

  it('#getInterviews should return an observable interviews', () => {
    const interviewsResponse: InterviewResponse[] = INTERVIEWS_RESPONSE;

    service.getInterviews().subscribe(interviews => {
      for (const [i, interview] of interviews.entries()) {
        verifyInterviewContent(interview, interviewsResponse[i]);
      }
    });

    const req = httpMock.expectOne(environment.baseUrl + 'interviews');
    expect(req.request.method).toEqual('GET');

    req.flush(interviewsResponse);
    httpMock.verify();
  });

  it('#getInterview should return an observable of an interview', () => {
    const interviewResponse: InterviewResponse = INTERVIEWS_RESPONSE[0];

    service.getInterview(1).subscribe(interview => {
      verifyInterviewContent(interview, interviewResponse);
    });

    const req = httpMock.expectOne(environment.baseUrl + 'interviews/' + 1);
    expect(req.request.method).toEqual('GET');

    req.flush(interviewResponse);
    httpMock.verify();
  });

  it('#postInterview should return an observable of an interview', () => {
    const interviewResponse: InterviewResponse = INTERVIEWS_RESPONSE[0];

    service.postInterview(INTERVIEWS[0], FACCRITS).subscribe(interview => {
      verifyInterviewContent(interview, interviewResponse);
    });

    const req = httpMock.expectOne(environment.baseUrl + 'interviews');
    expect(req.request.method).toEqual('POST');

    req.flush(interviewResponse);
    httpMock.verify();
  });

  it('#putInterview should return an observable of an interview', () => {
    const interviewResponse: InterviewResponse = INTERVIEWS_RESPONSE[0];

    service.putInterview(INTERVIEWS[0]).subscribe(interview => {
      verifyInterviewContent(interview, interviewResponse);
    });

    const req = httpMock.expectOne(environment.baseUrl + 'interviews/' + INTERVIEWS[0].id);
    expect(req.request.method).toEqual('PUT');

    req.flush(interviewResponse);
    httpMock.verify();
  });

  function verifyInterviewContent(interview: Interview, interviewDto: InterviewResponse) {
    const { id, status, startDate, contactPersons, answers, auditId, note } = interview;
    expect(id).toEqual(interviewDto.id);
    expect(startDate).toEqual(new Date(interviewDto.startDate).getTime());
    expect(status).toEqual(interviewDto.status);
    expect(contactPersons).toEqual(interviewDto.interviewedContactPersons);
    expect(answers).toEqual(interviewDto.answers);
    expect(auditId).toEqual(interviewDto.auditId);
    expect(note).toEqual(interviewDto.note);
  }
});
