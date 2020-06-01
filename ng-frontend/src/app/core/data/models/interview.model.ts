import { ContactPerson } from './contact-person.model';
import { FacCrit } from './faccrit.model';

export enum InterviewStatus {
  InAction,
  Finished,
}

export interface Interview {
  id?: string;
  start?: number;
  end?: number;
  contactPeople?: ContactPerson[];
  status: InterviewStatus;
  facCrit: FacCrit;
}
