import { CustomerData } from './customer-data.model';
import { ContactPerson } from './contact-person.model';
import { IsoCategory } from './iso-category.model';

export interface Audit {
  name: string;
  start?: number;
  end?: number;

  customerData: CustomerData;
  contactPerson: ContactPerson;
  categories: IsoCategory[];
}
