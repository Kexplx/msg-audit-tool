import { Audit, AuditStatus } from '../models/audit.model';
import { factors } from '../factors';
import * as shortid from 'shortid';

export const audits: Audit[] = [
  {
    name: 'BMW Personalaudit',
    start: new Date(2021, 3, 22).getTime(),
    end: new Date(2022, 5, 12).getTime(),
    contactPerson: {
      firstName: 'Oscar',
      lastName: 'Rosner',
      information: 'test.test@web.de oder 01929/1293912',
      title: 'Dr.',
      salutation: 'Herr',
    },
    customerData: {
      department: 'Personalabteilung',
      name: 'BMW',
      sector: 'Automobilindustrie',
    },
    status: AuditStatus.IsPlanned,
    factors: factors.slice(0, 2),
    id: shortid.generate(),
    creationDate: Date.now(),
  },
  {
    name: 'Audi IT-Support',
    start: new Date().getTime(),
    end: new Date(2025, 9, 2).getTime(),
    contactPerson: {
      firstName: 'Cathy',
      lastName: 'Hu',
      information: 'test.test@web.de oder 01929/1293912',
      title: 'Dr.',
      salutation: 'Frau',
    },
    customerData: {
      department: 'IT-Support',
      name: 'Audi',
      sector: 'Automobilindustrie',
    },
    status: AuditStatus.InAction,
    factors: factors.slice(3, 7),
    id: shortid.generate(),
    creationDate: Date.now(),
  },
  {
    name: 'VW Kantine',
    start: new Date().getTime(),
    end: new Date(2028, 10, 18).getTime(),
    contactPerson: {
      firstName: 'David',
      lastName: 'Leicht',
      information: 'test.test@web.de oder 01929/1293912',
      title: 'Dr. ',
      salutation: 'Herr',
    },
    customerData: {
      department: 'Kantine',
      name: 'VW',
      sector: 'Automobilindustrie',
    },
    status: AuditStatus.IsCanceled,
    factors: factors.slice(6, 2),
    id: shortid.generate(),
    creationDate: Date.now(),
  },
  {
    name: '3M Neu',
    start: new Date().getTime(),
    end: new Date(2028, 4, 18).getTime(),
    contactPerson: {
      firstName: 'David',
      lastName: 'Leicht',
      information: 'test.test@web.de oder 01929/1293912',
      title: 'Dr. ',
      salutation: 'Herr',
    },
    customerData: {
      department: 'Kantine',
      name: 'VW',
      sector: 'Automobilindustrie',
    },
    status: AuditStatus.IsFinished,
    factors: factors.slice(6, 2),
    id: shortid.generate(),
    creationDate: Date.now(),
  },
];
