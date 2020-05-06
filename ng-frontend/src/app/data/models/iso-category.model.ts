export interface Category {
  title: string;
  questions?: string[];
  children?: Category[];
}
