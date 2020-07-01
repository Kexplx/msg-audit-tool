import { FacCrit } from '../../data/models/faccrit.model';
import { ContactPerson } from '../../data/models/contact-person.model';
import { AuditStatus } from '../../data/models/audit.model';

export interface AuditDto {
  auditId: number;
  auditName: string;
  startDate: string;
  endDate: string;
  creationDate: string;
  status: AuditStatus;
  scope: FacCrit[];
  contactPeople: ContactPerson[];
  cancellationDate: string;
  cancellationReason: string;
  cancellationContactPerson: ContactPerson[];
}
