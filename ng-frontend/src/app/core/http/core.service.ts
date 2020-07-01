import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FacCrit } from '../data/models/faccrit.model';
import { Audit } from '../data/models/audit.model';
import { AuditDto } from './dtos/audit.dto';
import { audit, map } from 'rxjs/operators';

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

  //

  //

  //

  /**
   * Sends a GET to ../faccrits and returns an observable.
   */
  getFacCrits() {
    return this.http.get<FacCrit[]>(compileTimeSwitchedString + '/faccrits');
  }

  /**
   * Sends a GET to ../audits and returns an observable.
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
}
