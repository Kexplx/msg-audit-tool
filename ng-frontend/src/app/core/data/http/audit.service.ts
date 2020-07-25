import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Audit } from '../models/audit.model';
import { AuditResponse } from './response-models/audit.response';
import { map } from 'rxjs/operators';
import { AuditPostRequest } from './request-models/audit-post.request';
import { Observable } from 'rxjs';
import { AuditPutRequest } from './request-models/audit-put.request';
import { parseTimestamp } from 'src/app/core/data/helpers/date-helpers';
import { environment } from 'src/environments/environment';
import { AuditScopePutRequest } from './request-models/audit-scope-put.request';

@Injectable({
  providedIn: 'root',
})
export class AuditService {
  constructor(private http: HttpClient) {}

  getAudits(): Observable<Audit[]> {
    return this.http.get(environment.baseUrl + 'audits').pipe(
      map((audits: AuditResponse[]) => {
        const result = audits.map<Audit>(auditDto => {
          const endDate = auditDto.endDate ? new Date(auditDto.startDate).getTime() : null;

          return {
            id: auditDto.id,
            name: auditDto.name,
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

  getAudit(id: number): Observable<Audit> {
    return this.http.get<AuditResponse>(environment.baseUrl + 'audits/' + id).pipe(
      map<AuditResponse, Audit>(auditDto => {
        const endDate = auditDto.endDate ? new Date(auditDto.startDate).getTime() : null;

        return {
          id: auditDto.id,
          name: auditDto.name,
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
    const auditDto: AuditPostRequest = {
      name: audit.name,
      endDate: parseTimestamp(audit.endDate),
      contactPersons: audit.contactPersons?.map(x => x.id) ?? [],
      scope: audit.scope?.map(x => x.id) ?? [],
      startDate: parseTimestamp(audit.startDate),
    };

    return this.http.post(environment.baseUrl + 'audits', auditDto).pipe(
      map((auditDto: AuditResponse) => {
        const endDate = auditDto.endDate ? new Date(auditDto.startDate).getTime() : null;

        return {
          id: auditDto.id,
          name: auditDto.name,
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

  putAudit(oldAudit: Audit, newAudit: Audit): Observable<Audit> {
    this.putAuditScope(oldAudit, newAudit);
    this.putAuditContactPersons(oldAudit, newAudit);

    const putAuditDto: AuditPutRequest = {
      name: newAudit.name,
      endDate: parseTimestamp(newAudit.endDate),
      status: newAudit.status,
      startDate: parseTimestamp(newAudit.startDate),
    };

    return this.http.put<Audit>(environment.baseUrl + 'audits/' + newAudit.id, putAuditDto).pipe(
      map(auditDto => {
        const endDate = auditDto.endDate ? new Date(auditDto.startDate).getTime() : null;

        return {
          id: auditDto.id,
          name: auditDto.name,
          creationDate: new Date(auditDto.creationDate).getTime(),
          startDate: new Date(auditDto.startDate).getTime(),
          endDate,
          scope: newAudit.scope,
          status: auditDto.status,
          contactPersons: newAudit.contactPersons,
        };
      }),
    );
  }

  /**
   * Comapres the contact persons of an old and an updated audit.
   * Makes PUT and DELETE requests to ../audits/{id}/contactpersons/{id} to update
   * the contact persons of the audit accordingly.
   *
   * @param oldAudit The old audit.
   * @param newAudit The updated audit.
   */
  private putAuditContactPersons(oldAudit: Audit, newAudit: Audit): void {
    const url = environment.baseUrl + 'audits/' + newAudit.id + '/contactpersons/';

    const oldContactPersons = oldAudit.contactPersons;
    const newContactPersons = newAudit.contactPersons;

    for (const contactPerson of newContactPersons) {
      const existsInOld = oldAudit.contactPersons.find(x => x.id === contactPerson.id);
      if (!existsInOld) {
        this.http.put(url + contactPerson.id, {}).subscribe(() => {});
      }
    }

    for (const contactPerson of oldContactPersons) {
      const existsInUpdated = newAudit.contactPersons.find(x => x.id === contactPerson.id);
      if (!existsInUpdated) {
        this.http.delete(url + contactPerson.id, {}).subscribe(() => {});
      }
    }
  }

  /**
   * Updates the audits scope.
   *
   * Compares the old and new scope of an audit and
   * calls the PUT endpoint with the removed flag set to
   * true (FacCrit will be removed) or false (FacCrit will be added).
   * @param oldAudit The old audit.
   * @param newAudit The updated audit.
   */
  private putAuditScope(oldAudit: Audit, newAudit: Audit): void {
    const url = environment.baseUrl + 'audits/' + newAudit.id + '/scope';

    const oldScope = oldAudit.scope;
    const newScope = newAudit.scope;

    for (const facCrit of newScope) {
      const existsInOld = oldAudit.scope.find(x => x.id === facCrit.id);
      if (!existsInOld) {
        const auditScopeChange: AuditScopePutRequest = {
          facCritId: facCrit.id,
          changeNote: 'Change note for a scope item',
          note: 'Note for a scope item',
          removed: false, // FacCrit will be addded to scope
        };

        this.http.put(url, auditScopeChange).subscribe(() => {});
      }
    }

    for (const facCrit of oldScope) {
      const existsInNew = newAudit.scope.find(x => x.id === facCrit.id);
      if (!existsInNew) {
        const auditScopeChange: AuditScopePutRequest = {
          facCritId: facCrit.id,
          changeNote: 'Change note for a scope item',
          note: 'Note for a scope item',
          removed: true, // FacCrit will be removed from scope
        };

        this.http.put(url, auditScopeChange).subscribe(() => {});
      }
    }
  }
}
