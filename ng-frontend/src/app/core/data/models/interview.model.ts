import { ContactPerson } from './contact-person.model';
import { Answer } from './answer.model';

export enum InterviewStatus {
  Active = 'ACTIVE',
  Finished = 'FINISHED',
}

export interface Interview {
  id?: number;
  auditId?: number;
  startDate?: number;
  endDate?: number;
  status: InterviewStatus;
  note?: string;
  contactPersons: ContactPerson[];
  answers?: Answer[];
}
