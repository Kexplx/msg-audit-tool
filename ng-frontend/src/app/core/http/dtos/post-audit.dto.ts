export interface PostAuditDto {
  auditName: string;
  startDate: string;
  endDate: string;
  scope: number[];
  contactPersons: number[];
}
