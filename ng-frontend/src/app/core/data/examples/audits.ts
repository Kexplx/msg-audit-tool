import { Audit, AuditStatus } from '../models/audit.model';
import { FACCRITS } from './fac-crits';
import { CONTACT_PEOPLE } from './contact-people';
import { InterviewStatus } from '../models/interview.model';

export const AUDITS: Audit[] = [
  {
    id: '1gdfss23',
    creationDate: Date.now(),
    name: 'Audit 1',
    startDate: Date.now(),
    facCrits: FACCRITS,
    status: AuditStatus.Planned,
    interviews: [
      {
        id: '23894',
        contactPeople: CONTACT_PEOPLE,
        facCrits: FACCRITS,
        status: InterviewStatus.InAction,
        start: Date.now(),
      },
    ],
  },
  {
    id: '12sgdfgfd3',
    creationDate: Date.now(),
    name: 'Audit 2',
    startDate: Date.now(),
    facCrits: [],
    status: AuditStatus.Planned,
  },
  {
    id: '1s2sgdsdgffgfd3',
    creationDate: Date.now(),
    name: 'Audit 3',
    startDate: Date.now(),
    facCrits: [],
    status: AuditStatus.InAction,
  },
  {
    id: '12sgdfggsdffd3',
    creationDate: Date.now(),
    name: 'Audit 4',
    startDate: Date.now(),
    facCrits: [],
    status: AuditStatus.InAction,
  },
];
