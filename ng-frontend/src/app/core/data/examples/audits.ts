import { Audit, AuditStatus } from '../models/audit.model';
import { contactPeople } from './contact-people';

export const audits: Audit[] = [
  {
    id: '1gdfss23',
    creationDate: Date.now(),
    name: 'Audit 1',
    startDate: Date.now(),
    facCrits: [],
    status: AuditStatus.InAction,
    contactPeople,
  },
  {
    id: '12sgdfgfd3',
    creationDate: Date.now(),
    name: 'Audit 2',
    startDate: Date.now(),
    facCrits: [],
    status: AuditStatus.Finished,
    contactPeople,
  },
  {
    id: '1s2sgdsdgffgfd3',
    creationDate: Date.now(),
    name: 'Audit 2',
    startDate: Date.now(),
    facCrits: [],
    status: AuditStatus.Finished,
    contactPeople,
  },
  {
    id: '12sgdfggsdffd3',
    creationDate: Date.now(),
    name: 'Audit 2',
    startDate: Date.now(),
    facCrits: [],
    status: AuditStatus.Finished,
    contactPeople,
  },
];
