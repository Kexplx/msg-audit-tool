import { InterviewStatus } from '../../models/interview.model';
import { Answer } from '../../models/answer.model';
import { ContactPerson } from '../../models/contact-person.model';

export interface InterviewDto {
  id?: number;
  auditId: number;
  startDate: string;
  endDate: string;
  note: string;
  status: InterviewStatus;
  answers: Answer[];
  interviewedContactPersons: ContactPerson[];
}
