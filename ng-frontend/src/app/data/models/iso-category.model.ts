export interface Category {
  title: string;
  questions?: string[];
  subcategories?: Category[];
}
