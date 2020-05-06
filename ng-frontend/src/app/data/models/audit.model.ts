import { CustomerData } from './customer-data.model';
import { ContactPerson } from './contact-person.model';
import { Category } from './category.model';

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

  customerData: CustomerData;
  contactPerson: ContactPerson;
  categories?: Category[];

  status: AuditStatus;
}
