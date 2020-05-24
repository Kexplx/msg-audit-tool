import { ContactPerson } from './contact-person.model';

export interface Interview {
  start?: number;
  end?: number;
  persons?: ContactPerson[];
}
