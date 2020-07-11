import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FacCrit } from '../data/models/faccrit.model';
import { Audit } from '../data/models/audit.model';
import { AuditDto } from './dtos/audit.dto';
import { map } from 'rxjs/operators';
import { PostAuditDto } from './dtos/post-audit.dto';
import { Observable } from 'rxjs';
import { PutAuditDto } from './dtos/put-audit.dto';
import { parseTimestamp } from 'src/app/core/data/helpers/date-helpers';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuditService {
  constructor(private http: HttpClient) {}

  /**
   * Builds an observable for making a GET request to get all audits.
   *
   * @returns An Observable of the audits.
   */
  getAudits(): Observable<Audit[]> {
    return this.http.get(environment.baseUrl + 'audits').pipe(
      map((audits: AuditDto[]) => {
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

  /**
   * Builds an observable for making a GET request to get an audit by id.
   *
   * @param id The audit's id.
   * @returns An Observable of the audit.
   */
  getAudit(id: number): Observable<Audit> {
    return this.http.get<AuditDto>(environment.baseUrl + 'audits/' + id).pipe(
      map<AuditDto, Audit>(auditDto => {
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

  /**
   * Builds an observable for making a POST request to creats an audit.
   *
   * @param audit The audit to create.
   * @returns An Observable of the created audit.
   */
  postAudit(audit: Audit): Observable<Audit> {
    const auditDto: PostAuditDto = {
      name: audit.name,
      endDate: parseTimestamp(audit.endDate),
      contactPersons: audit.contactPersons?.map(x => x.id) ?? [],
      scope: audit.scope?.map(x => x.id) ?? [],
      startDate: parseTimestamp(audit.startDate),
    };

    return this.http.post(environment.baseUrl + 'audits', auditDto).pipe(
      map((auditDto: AuditDto) => {
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

  /**
   * Builds an observable for making a PUT request to update an audit.
   *
   * @param audit The old audit.
   * @param audit The updated audit.
   * @returns An Observable of the updated audit.
   */
  putAudit(oldAudit: Audit, currentAudit: Audit): Observable<Audit> {
    this.putAuditContactPersons(oldAudit, currentAudit);
    const putAuditDto: PutAuditDto = {
      name: currentAudit.name,
      endDate: parseTimestamp(currentAudit.endDate),
      startDate: parseTimestamp(currentAudit.startDate),
    };

    return this.http
      .put<Audit>(environment.baseUrl + 'audits/' + currentAudit.id, putAuditDto)
      .pipe(
        map(auditDto => {
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

  /**
   * Comapres the contact persons of an old and an updated audit.
   * Makes PUT and DELETE requests to ../audits/{id}/contactpersons/{id} to update
   * the contact persons of the audit accordingly.
   *
   * @param oldAudit The old audit.
   * @param currentAudit The updated audit.
   */
  private putAuditContactPersons(oldAudit: Audit, currentAudit: Audit): void {
    const url = environment.baseUrl + 'audits/' + currentAudit.id + '/contactpersons/';

    const oldContactPersons = oldAudit.contactPersons;
    const newContactPersons = currentAudit.contactPersons;

    for (const contactPerson of newContactPersons) {
      const existsInOld = oldAudit.contactPersons.find(x => x.id === contactPerson.id);
      if (!existsInOld) {
        this.http.put(url + contactPerson.id, {}).subscribe(() => {});
      }
    }

    for (const contactPerson of oldContactPersons) {
      const existsInUpdated = currentAudit.contactPersons.find(x => x.id === contactPerson.id);
      if (!existsInUpdated) {
        this.http.delete(url + contactPerson.id, {}).subscribe(() => {});
      }
    }
  }
}
