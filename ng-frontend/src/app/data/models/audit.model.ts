import { CustomerData } from './customer-data.model';
import { ContactPerson } from './contact-person.model';

export interface Audit {
  name: string;
  start?: number;
  end?: number;

  customerData: CustomerData;
  contactPereson: ContactPerson;
}
