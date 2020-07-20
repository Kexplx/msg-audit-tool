import { InterviewStatus } from '../../models/interview.model';

export interface PutInterviewDto {
  startDate: string;
  endDate: string;
  status: InterviewStatus;
  note: string;
}
