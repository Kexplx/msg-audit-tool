import { CustomerData } from './customer-data.model';
import { ContactPerson } from './contact-person.model';
import { Factor } from './factor.model';

export interface Audit {
  id?: string;
  name: string;
  start?: number;
  end?: number;

  customerData: CustomerData;
  contactPerson: ContactPerson;
  factors?: Factor[];
}
