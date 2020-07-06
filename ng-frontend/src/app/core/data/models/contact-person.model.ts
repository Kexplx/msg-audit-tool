export enum Salutation {
  Frau = 'FRAU',
  Herr = 'HERR',
  Divers = 'DIVERS',
}

export interface ContactPerson {
  id?: number;
  salutation: Salutation;
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
