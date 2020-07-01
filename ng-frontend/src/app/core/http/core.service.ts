import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FacCrit } from '../data/models/faccrit.model';
import { Audit } from '../data/models/audit.model';
import { AuditDto } from './dtos/audit.dto';
import { map } from 'rxjs/operators';
import { PostAuditDto } from './dtos/post-audit.dto';
import { parseTimestamp } from './helpers';
import { Observable } from 'rxjs';

const connectionStrings = {
  production: '...',
  development: 'http://localhost:8080',
};

const compileTimeSwitchedString = connectionStrings.development;

@Injectable({
  providedIn: 'root',
})
export class CoreService {
  constructor(private http: HttpClient) {}

  /**
   * Sends a GET to ../faccrits and returns an observable.
   */
  getFacCrits() {
    return this.http.get<FacCrit[]>(compileTimeSwitchedString + '/faccrits');
  }

  /**
   * Sends a GET to ../audits and returns an observable.
   *
   * ../audits should return a list of all existing audits
   */
  getAudits() {
    return this.http.get(compileTimeSwitchedString + '/audits').pipe(
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
            contactPeople: auditDto.contactPeople,
          };
        });
        return result;
      }),
    );
  }

  /**
   * Sends a POST to ../audits and returns an observable.
   *
   * ../contactpersons should return the created audit
   */
  postAudit(audit: Audit): Observable<Audit> {
    const auditDto: PostAuditDto = {
      auditName: audit.name,
      endDate: parseTimestamp(audit.endDate),
      contactPeople: audit.contactPeople?.map(x => x.id) ?? [],
      scope: audit.scope?.map(x => x.id) ?? [],
      startDate: parseTimestamp(audit.startDate),
    };

    return this.http.post(compileTimeSwitchedString + '/audits', auditDto).pipe(
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
          contactPeople: auditDto.contactPeople,
        };
      }),
    );
  }

  /**
   * Sends a PUT to ../contactpersons/{id} and returns an observable
   *
   * PUT ../contactpersons returns the updated contact person
   */
  updateAudit(audit: Audit) {
    const putAuditDto: PutAuditDto = {
      auditName: audit.name,
      endDate: parseTimestamp(audit.endDate),
      startDate: parseTimestamp(audit.startDate),
    };

    return this.http.put(compileTimeSwitchedString + '/audits/' + audit.id, putAuditDto);
  }
  /**
   * Sends a POST to ../contactpersons and returns an observable
   *
   * ../contactpersons should return a list of all existing contact persons
   */
  getContactPersons() {
    const obs$ = this.http.get(compileTimeSwitchedString + '/contactpersons');
  }
}
