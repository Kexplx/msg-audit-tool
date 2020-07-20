import { Audit, AuditStatus } from 'src/app/core/data/models/audit.model';
import { Salutation } from 'src/app/core/data/models/contact-person.model';

export const AUDITS: Audit[] = [
  {
    id: 1,
    name: 'MSG project audit',
    startDate: Date.now(),
    endDate: Date.now(),
    creationDate: Date.now(),
    status: AuditStatus.Active,
    scope: [
      {
        id: 2,
        referenceId: 3,
        goal: 'asd',
        name: 'Modifizierbarkeit',
      },
    ],
    contactPersons: [
      {
        id: 4,
        salutation: Salutation.Herr,
        title: 'Prof',
        forename: 'Max',
        role: 'asds',
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
    name: 'MSG project audit',
    startDate: Date.now(),
    endDate: Date.now(),
    creationDate: Date.now(),
    status: AuditStatus.Active,
    scope: [
      {
        id: 2,
        referenceId: 12,
        name: 'Modifizierbarkeit',
        goal: 'asd',
      },
    ],
    contactPersons: [
      {
        id: 1,
        salutation: Salutation.Herr,
        title: 'Prof',
        forename: 'Max',
        role: 'asds',
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
