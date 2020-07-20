import { InterviewService } from '../../interview.service';
import { of } from 'rxjs';
import { INTERVIEWS } from '../dummies/app-models/interviews';

export const interviewServiceSpy: jasmine.SpyObj<InterviewService> = jasmine.createSpyObj(
  'InterviewService',
  ['getInterview', 'getInterviews', 'postInterview', 'putInterview', 'getInterviewsByAuditId'],
);

interviewServiceSpy.getInterviews.and.returnValue(of(INTERVIEWS));
interviewServiceSpy.getInterviewsByAuditId.and.returnValue(of(INTERVIEWS));
interviewServiceSpy.getInterview.and.returnValue(of(INTERVIEWS[0]));
interviewServiceSpy.postInterview.and.returnValue(of(INTERVIEWS[0]));
interviewServiceSpy.putInterview.and.returnValue(of(INTERVIEWS[0]));
