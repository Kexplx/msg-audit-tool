import { InterviewStatus } from '../../data/models/interview.model';

export interface PutInterviewDto {
  startDate: string;
  endDate: string;
  status: InterviewStatus;
  goal: string;
}
