import { CustomerData } from './customer-data.model';
import { ContactPerson } from './contact-person.model';
import { Factor } from './factor.model';
import { Interview } from './interview.model';

export enum AuditStatus {
  IsPlanned,
  InAction,
  IsFinished,
  IsCanceled,
}

export interface Audit {
  id?: string;
  name: string;
  start?: number;
  end?: number;
  customerData?: CustomerData;
  contactPerson?: ContactPerson;
  scope?: Factor[];
  status: AuditStatus;
  creationDate: number;
  interviews?: Interview[];
}
