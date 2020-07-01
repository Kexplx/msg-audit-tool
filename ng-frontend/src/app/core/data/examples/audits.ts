import { Audit, AuditStatus } from '../models/audit.model';
import { FACCRITS } from './fac-crits';

export const AUDITS: Audit[] = [
  {
    id: 1293921,
    creationDate: Date.now(),
    name: 'Audit 1',
    startDate: Date.now(),
    scope: FACCRITS,
    status: AuditStatus.Planned,
  },
  {
    id: 9832093,
    creationDate: Date.now(),
    name: 'Audit 2',
    startDate: Date.now(),
    scope: [],
    status: AuditStatus.Planned,
  },
  {
    id: 53432,
    creationDate: Date.now(),
    name: 'Audit 3',
    startDate: Date.now(),
    scope: [],
    status: AuditStatus.Active,
  },
  {
    id: 3425345,
    creationDate: Date.now(),
    name: 'Audit 4',
    startDate: Date.now(),
    scope: [],
    status: AuditStatus.Active,
  },
];
