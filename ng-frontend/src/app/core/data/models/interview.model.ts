import { ContactPerson } from './contact-person.model';
import { FacCrit } from './faccrit.model';
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
  goal?: string;
  contactPeople: ContactPerson[];
  answers?: Answer[];
}
