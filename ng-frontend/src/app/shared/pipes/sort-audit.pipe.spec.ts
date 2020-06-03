import { SortAuditPipe } from './sort-audit.pipe';
import { AuditStatus } from 'src/app/core/data/models/audit.model';
import { factors } from 'src/app/core/data/examples/fac-crits';

describe('SortAuditPipe', () => {
  const audit0 = {
    name: 'TestAudit0',
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
    status: AuditStatus.Planned,
    factors: factors.slice(0, 2),
    id: '0',
    creationDate: new Date(2020, 3, 1).getTime(),
  };

  const audit1 = {
    name: 'TestAudit1',
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
    status: AuditStatus.Planned,
    factors: factors.slice(0, 2),
    id: '1',
    creationDate: new Date(2020, 1, 1).getTime(),
  };

  const audit2 = {
    name: 'TestAudit2',
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
    status: AuditStatus.Planned,
    factors: factors.slice(0, 2),
    id: '2',
    creationDate: new Date(2020, 2, 1).getTime(),
  };

  it('create an instance', () => {
    const pipe = new SortAuditPipe();
    expect(pipe).toBeTruthy();
  });

  it('should sort an Audit[] by creationDate in descending order', () => {
    const pipe = new SortAuditPipe();
    let audits = [audit0, audit1, audit2];
    let verifyAudits = [audit0, audit2, audit1];
    expect(pipe.transform(audits)).toEqual(verifyAudits);
  });
});
