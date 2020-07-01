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
  id?: number;
  status: AuditStatus;
  creationDate: number;
  name: string;
  startDate: number;
  endDate?: number;
  contactPeople?: ContactPerson[];
  interviews?: Interview[];
  scope: FacCrit[];
}
