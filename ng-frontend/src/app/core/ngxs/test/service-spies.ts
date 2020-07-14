import { InterviewService } from '../../http/interview.service';
import { ContactPersonService } from '../../http/contact-person.service';
import { of } from 'rxjs';
import { CONTACTPERSON_DTO_DUMMY } from '../../http/test/dummies/contact-persons';
import { INTERVIEWS_DUMMY } from '../../http/test/dummies/interviews';
import { QUESTIONS_DTO_DUMMY } from '../../http/test/dummies/questions';
import { FacCritService } from '../../http/facCrit.service';
import { FACCRITS_DUMMY } from '../../http/test/dummies/faccrits';
import { AuditService } from '../../http/audit.service';
import { AUDITS_DUMMY } from './dummies/audits';

export const contactPersonServiceSpy: jasmine.SpyObj<ContactPersonService> = jasmine.createSpyObj(
  'ContactPersonService',
  ['putContactPerson', 'getContactPersons', 'getContactPerson', 'postContactPerson'],
);

contactPersonServiceSpy.getContactPersons.and.returnValue(of(CONTACTPERSON_DTO_DUMMY));
contactPersonServiceSpy.getContactPerson.and.returnValue(of(CONTACTPERSON_DTO_DUMMY[0]));
contactPersonServiceSpy.postContactPerson.and.returnValue(of(CONTACTPERSON_DTO_DUMMY[0]));
contactPersonServiceSpy.putContactPerson.and.returnValue(of(CONTACTPERSON_DTO_DUMMY[1]));

export const interviewServiceSpy: jasmine.SpyObj<InterviewService> = jasmine.createSpyObj(
  'InterviewService',
  ['getInterview', 'getInterviews', 'postInterview', 'putInterview', 'getInterviewsByAuditId'],
);

interviewServiceSpy.getInterviews.and.returnValue(of(INTERVIEWS_DUMMY));
interviewServiceSpy.getInterviewsByAuditId.and.returnValue(of(INTERVIEWS_DUMMY));
interviewServiceSpy.getInterview.and.returnValue(of(INTERVIEWS_DUMMY[0]));
interviewServiceSpy.postInterview.and.returnValue(of(INTERVIEWS_DUMMY[0]));
interviewServiceSpy.putInterview.and.returnValue(of(INTERVIEWS_DUMMY[0]));

export const questionServiceSpy = jasmine.createSpyObj('QuestionService', ['getQuestion']);
questionServiceSpy.getQuestion.and.returnValue(of(QUESTIONS_DTO_DUMMY[0]));

export const facCritServiceSpy: jasmine.SpyObj<FacCritService> = jasmine.createSpyObj(
  'FacCritService',
  ['getFacCrits', 'getFacCritsByInterviewId'],
);

facCritServiceSpy.getFacCrits.and.returnValue(of(FACCRITS_DUMMY));
facCritServiceSpy.getFacCritsByInterviewId.and.returnValue(of(FACCRITS_DUMMY));

export const auditServiceSpy: jasmine.SpyObj<AuditService> = jasmine.createSpyObj('AuditService', [
  'getAudits',
  'getAudit',
  'postAudit',
  'putAudit',
]);

auditServiceSpy.getAudits.and.returnValue(of(AUDITS_DUMMY));
auditServiceSpy.getAudit.and.returnValue(of(AUDITS_DUMMY[0]));
auditServiceSpy.postAudit.and.returnValue(of(AUDITS_DUMMY[0]));
auditServiceSpy.putAudit.and.returnValue(of(AUDITS_DUMMY[1]));
