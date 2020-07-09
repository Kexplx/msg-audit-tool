import { InterviewDto } from '../../dtos/interview.dto';
import { InterviewStatus } from 'src/app/core/data/models/interview.model';
import { Salutation } from 'src/app/core/data/models/contact-person.model';

export const INTERVIEWS_DTO_DUMMY: InterviewDto[] = [
  {
    id: 1,
    auditId: 3,
    startDate: '2020-07-08',
    endDate: '2020-07-08',
    goal: 'Das Ziel des Audits ist ...',
    status: InterviewStatus.Active,
    answers: [
      {
        questionId: 1,
        interviewId: 3,
        faccritId: 1,
        result: false,
        responsible: false,
        documentation: false,
        procedure: false,
        reason: 'reason',
        proof: 'proof',
        annotation: 'annotation',
      },
    ],
    interviewedContactPersons: [
      {
        id: 1,
        salutation: Salutation.Herr,
        role: 'Default role',
        title: 'Prof',
        forename: 'Max',
        surname: 'Mustermann',
        contactInformation: 'max.mustermann@gmx.de, tel: 0123456789',
        companyName: 'msg systems AG',
        department: 'Softwareentwicklung',
        sector: 'msg Public Sector',
        corporateDivision: 'Software',
      },
    ],
  },
  {
    id: 2,
    auditId: 4,
    startDate: '2020-07-08',
    endDate: '2020-07-08',
    goal: 'Das Ziel des Audits ist ...',
    status: InterviewStatus.Active,
    answers: [
      {
        questionId: 1,
        interviewId: 3,
        faccritId: 1,
        result: false,
        responsible: false,
        documentation: false,
        procedure: false,
        reason: 'reason',
        proof: 'proof',
        annotation: 'annotation',
      },
    ],
    interviewedContactPersons: [
      {
        id: 1,
        salutation: Salutation.Herr,
        role: 'Default role',
        title: 'Prof',
        forename: 'Max',
        surname: 'Mustermann',
        contactInformation: 'max.mustermann@gmx.de, tel: 0123456789',
        companyName: 'msg systems AG',
        department: 'Softwareentwicklung',
        sector: 'msg Public Sector',
        corporateDivision: 'Software',
      },
    ],
  },
];
