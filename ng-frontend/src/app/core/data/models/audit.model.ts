import { ContactPerson } from './contact-person.model';
import { Interview } from './interview.model';
import { FacCrit } from './faccrit.model';

export enum AuditStatus {
  Planned,
  InAction,
  Finished,
  Cancelled,
}

export interface Audit {
  id?: string;
  status: AuditStatus;
  creationDate: number;
  name: string;
  start: number;
  end?: number;
  contactPeople?: ContactPerson[];
  interviews?: Interview[];
  facCrits: FacCrit[];
}
