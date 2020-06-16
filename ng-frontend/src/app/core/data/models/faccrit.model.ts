import { Question } from './question.model';

export interface FacCrit {
  id: string;
  referenceId: string;
  name: string;
  questions: Question[];
}
