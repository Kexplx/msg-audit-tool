import { Interview, InterviewStatus } from '../models/interview.model';
import { CONTACT_PEOPLE } from './contact-people';
import { FACCRITS } from './fac-crits';

export const INTERVIEWS: Interview[] = [
  {
    auditId: '1gdfss23',
    id: '23894',
    contactPeople: CONTACT_PEOPLE,
    facCrits: FACCRITS,
    status: InterviewStatus.InAction,
    start: Date.now(),
  },
];
