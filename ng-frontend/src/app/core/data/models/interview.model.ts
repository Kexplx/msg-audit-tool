import { ContactPerson } from './contact-person.model';

export interface Interview {
  id: string;
  start?: number;
  end?: number;
  persons?: ContactPerson[];
}
