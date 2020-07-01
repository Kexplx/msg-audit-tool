import { ContactPerson } from './contact-person.model';
import { FacCrit } from './faccrit.model';

export enum InterviewStatus {
  InAction,
  Finished,
}

export interface Interview {
  id?: number;
  auditId?: number;
  start?: number;
  end?: number;
  contactPeople: ContactPerson[];
  status: InterviewStatus;
  facCrits: FacCrit[];
  goal?: string;
}
