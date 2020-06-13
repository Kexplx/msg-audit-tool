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
  // contactPeopleRoles?: { contactPerson: ContactPerson; role: string }[];
  contactPeople: ContactPerson[];
  status: InterviewStatus;
  facCrits: FacCrit[];
}
