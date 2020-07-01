export interface ContactPerson {
  id?: number;
  salutation: string;
  title: string;
  forename: string;
  surname: string;
  companyName: string;
  role: string;
  sector: string;
  department: string;
  corporateDivision: string;
  contactInformation?: string;
}
