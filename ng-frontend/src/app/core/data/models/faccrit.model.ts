import { Question } from './question.model';

export interface FacCrit {
  id: number;
  referenceId: number;
  name: string;
  questions: Question[];
}
