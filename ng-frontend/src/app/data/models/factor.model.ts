import { Category } from './category.model';

export interface Factor {
  id?: number;
  title: string;
  categories: Category[];
}
