import { ContactPerson } from './contact-person.model';
import { Criteria } from './criteria.model';
import { Factor } from './factor.model';

export interface Interview {
  id?: string;
  start?: number;
  end?: number;
  persons?: ContactPerson[];
  criteria: Criteria;
  factorTitle?: string;
}
