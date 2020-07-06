import { ContactPerson } from './contact-person.model';
import { FacCrit } from './faccrit.model';

export enum AuditStatus {
  Planned = 'OPEN',
  Active = 'ACTIVE',
  Finished = 'FINISHED',
  Cancelled = 'CANCELLED',
}

export interface Audit {
  id?: number;
  status: AuditStatus;
  creationDate?: number;
  name: string;
  startDate: number;
  endDate?: number;
  contactPersons?: ContactPerson[];
  scope: FacCrit[];
}
