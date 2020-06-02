import { ContactPerson } from './contact-person.model';
import { Criteria } from './criteria.model';

export enum InterviewStatus {
  InAction,
  Finished,
}

export interface Interview {
  id?: string;
  start?: number;
  end?: number;
  persons?: ContactPerson[];
  criteria: Criteria;
  status: InterviewStatus;
  factorTitle?: string;
}
