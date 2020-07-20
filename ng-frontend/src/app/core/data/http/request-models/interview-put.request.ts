import { InterviewStatus } from '../../models/interview.model';

export interface InterviewPutRequest {
  startDate: string;
  endDate: string;
  status: InterviewStatus;
  note: string;
}
