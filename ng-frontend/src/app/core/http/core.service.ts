import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FacCrit } from '../data/models/faccrit.model';
import { Audit } from '../data/models/audit.model';
import { AuditDto } from './dtos/audit.dto';
import { map } from 'rxjs/operators';
import { PostAuditDto } from './dtos/post-audit.dto';
import { parseTimestamp } from './helpers';
import { Observable } from 'rxjs';
import { ContactPerson } from '../data/models/contact-person.model';
import { PutAuditDto } from './dtos/put-audit.dto';
import { compileTimeSwitchedString } from './connectionStrings';

@Injectable({
  providedIn: 'root',
})
export class CoreService {
  constructor(private http: HttpClient) {}

  getFacCrits() {
    return this.http.get<FacCrit[]>(compileTimeSwitchedString + 'facCrits');
  }

  getAudits() {
    return this.http.get(compileTimeSwitchedString + 'audits').pipe(
      map((audits: AuditDto[]) => {
        const result = audits.map<Audit>(auditDto => {
          const endDate = auditDto.endDate ? new Date(auditDto.startDate).getTime() : null;

          return {
            id: auditDto.auditId,
            name: auditDto.auditName,
            creationDate: new Date(auditDto.creationDate).getTime(),
            startDate: new Date(auditDto.startDate).getTime(),
            endDate,
            scope: auditDto.scope,
            status: auditDto.status,
            contactPersons: auditDto.contactPersons,
          };
        });
        return result;
      }),
    );
  }

  getAudit(id: number) {
    return this.http.get<AuditDto>(compileTimeSwitchedString + 'audits/' + id).pipe(
      map<AuditDto, Audit>(auditDto => {
        const endDate = auditDto.endDate ? new Date(auditDto.startDate).getTime() : null;

        return {
          id: auditDto.auditId,
          name: auditDto.auditName,
          creationDate: new Date(auditDto.creationDate).getTime(),
          startDate: new Date(auditDto.startDate).getTime(),
          endDate,
          scope: auditDto.scope,
          status: auditDto.status,
          contactPersons: auditDto.contactPersons,
        };
      }),
    );
  }

  postAudit(audit: Audit): Observable<Audit> {
    const auditDto: PostAuditDto = {
      auditName: audit.name,
      endDate: parseTimestamp(audit.endDate),
      contactPersons: audit.contactPersons?.map(x => x.id) ?? [],
      scope: audit.scope?.map(x => x.id) ?? [],
      startDate: parseTimestamp(audit.startDate),
    };

    return this.http.post(compileTimeSwitchedString + 'audits', auditDto).pipe(
      map((auditDto: AuditDto) => {
        const endDate = auditDto.endDate ? new Date(auditDto.startDate).getTime() : null;

        return {
          id: auditDto.auditId,
          name: auditDto.auditName,
          creationDate: new Date(auditDto.creationDate).getTime(),
          startDate: new Date(auditDto.startDate).getTime(),
          endDate,
          scope: auditDto.scope,
          status: auditDto.status,
          contactPersons: auditDto.contactPersons,
        };
      }),
    );
  }

  putAudit(oldAudit: Audit, currentAudit: Audit) {
    this.putAuditContactPersons(oldAudit, currentAudit);
    const putAuditDto: PutAuditDto = {
      auditName: currentAudit.name,
      endDate: parseTimestamp(currentAudit.endDate),
      startDate: parseTimestamp(currentAudit.startDate),
    };

    return this.http.put(compileTimeSwitchedString + 'audits/' + currentAudit.id, putAuditDto);
  }

  private putAuditContactPersons(oldAudit: Audit, currentAudit: Audit) {
    const url = compileTimeSwitchedString + 'audits/' + currentAudit.id + '/contactpersons/';
    const oldContactPersons = oldAudit.contactPersons;
    const newContactPersons = currentAudit.contactPersons;

    for (const contactPerson of newContactPersons) {
      const existsInOld = oldAudit.contactPersons.find(x => x.id === contactPerson.id);
      if (!existsInOld) {
        this.http.put(url + contactPerson.id, {}).subscribe(() => {});
      }
    }

    for (const contactPerson of oldContactPersons) {
      const existsInCurrent = currentAudit.contactPersons.find(x => x.id === contactPerson.id);
      if (!existsInCurrent) {
        this.http.delete(url + contactPerson.id, {}).subscribe(() => {});
      }
    }
  }

  getContactPersons() {
    return this.http.get<ContactPerson[]>(compileTimeSwitchedString + 'contactpersons');
  }

  postContactPerson(contactPerson: ContactPerson) {
    return this.http.post<ContactPerson>(
      compileTimeSwitchedString + 'contactpersons',
      contactPerson,
    );
  }

  putContactPerson(contactPerson: ContactPerson) {
    const url = compileTimeSwitchedString + 'contactpersons/' + contactPerson.id;
    return this.http.put<ContactPerson>(url, contactPerson);
  }
}
