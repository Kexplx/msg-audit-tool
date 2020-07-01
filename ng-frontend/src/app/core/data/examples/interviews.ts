import { Interview, InterviewStatus } from '../models/interview.model';
import { CONTACT_PEOPLE } from './contact-people';
import { FACCRITS } from './fac-crits';

export const INTERVIEWS: Interview[] = [
  {
    auditId: 65,
    id: 1232,
    contactPeople: CONTACT_PEOPLE,
    facCrits: FACCRITS,
    status: InterviewStatus.Active,
    start: Date.now(),
  },
];
