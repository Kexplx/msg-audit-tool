import { Audit, AuditStatus } from '../models/audit.model';

export const AUDITS: Audit[] = [
  {
    id: '1gdfss23',
    creationDate: Date.now(),
    name: 'Audit 1',
    startDate: Date.now(),
    facCrits: [],
    status: AuditStatus.InAction,
  },
  {
    id: '12sgdfgfd3',
    creationDate: Date.now(),
    name: 'Audit 2',
    startDate: Date.now(),
    facCrits: [],
    status: AuditStatus.Finished,
  },
  {
    id: '1s2sgdsdgffgfd3',
    creationDate: Date.now(),
    name: 'Audit 2',
    startDate: Date.now(),
    facCrits: [],
    status: AuditStatus.Finished,
  },
  {
    id: '12sgdfggsdffd3',
    creationDate: Date.now(),
    name: 'Audit 2',
    startDate: Date.now(),
    facCrits: [],
    status: AuditStatus.Finished,
  },
];
