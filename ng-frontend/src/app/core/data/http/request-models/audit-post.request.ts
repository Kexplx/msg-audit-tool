export interface AuditPostRequest {
  name: string;
  startDate: string;
  endDate: string;
  scope: number[];
  contactPersons: number[];
}
