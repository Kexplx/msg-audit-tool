export interface ContactPerson {
  id?: number;
  salutation: string;
  title: string;
  firstName: string;
  lastName: string;
  companyName: string;
  role: string;
  sector: string;
  department: string;
  corporateDivision: string;
  contactInformation?: string;
}
