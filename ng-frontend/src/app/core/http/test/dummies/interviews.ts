import { Interview, InterviewStatus } from 'src/app/core/data/models/interview.model';
import { Salutation } from 'src/app/core/data/models/contact-person.model';

export const INTERVIEWS_DUMMY: Interview[] = [
  {
    id: 1,
    auditId: 123456,
    startDate: Date.now(),
    endDate: Date.now(),
    note: 'Notiz',
    status: InterviewStatus.Active,
    answers: [
      {
        questionId: 1,
        interviewId: 1,
        faccritId: 1,
        result: false,
        responsible: false,
        documentation: false,
        procedure: false,
        reason: 'reason',
        proof: 'proof',
        annotation: 'annotation',
      },
      {
        questionId: 2,
        interviewId: 1,
        faccritId: 1,
        result: false,
        responsible: false,
        documentation: false,
        procedure: false,
        reason: 'reason',
        proof: 'proof',
        annotation: 'annotation',
      },
      {
        questionId: 1,
        interviewId: 1,
        faccritId: 4,
        result: false,
        responsible: false,
        documentation: false,
        procedure: false,
        reason: 'reason',
        proof: 'proof',
        annotation: 'annotation',
      },
    ],
    contactPersons: [
      {
        id: 1,
        salutation: Salutation.Herr,
        role: '123',
        title: 'Prof',
        forename: 'Max',
        surname: 'Mustermann',
        contactInformation: 'max.mustermann@gmx.de, tel: 0123456789',
        companyName: 'msg systems AG',
        department: 'Softwareentwicklung',
        sector: 'msg Public Sector',
        corporateDivision: 'Software',
      },
      {
        id: 2,
        role: '123',
        salutation: Salutation.Frau,
        title: 'Prof',
        forename: 'Maxi',
        surname: 'Mustermann',
        contactInformation: 'maxi.mustermann@gmx.de, tel: 0123456789',
        companyName: 'msg systems AG',
        department: 'Softwareentwicklung',
        sector: 'msg Public Sector',
        corporateDivision: 'Software',
      },
    ],
  },
];
