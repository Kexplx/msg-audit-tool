import { Criteria } from './criteria.model';

export interface Factor {
  id?: number;
  title: string;
  categories: Criteria[];
}
