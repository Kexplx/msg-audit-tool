import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Audit, AuditStatus } from '../data/models/audit.model';
import { map, audit } from 'rxjs/operators';
import { Observable, BehaviorSubject } from 'rxjs';
import { parseTimestamp } from 'src/app/core/data/helpers/date-helpers';
import { environment } from 'src/environments/environment';
import { AuditDto } from '../http/dtos/audit.dto';
import { PostAuditDto } from '../http/dtos/post-audit.dto';
import { PutAuditDto } from '../http/dtos/put-audit.dto';
import { AuditScopeChangeDto } from '../http/dtos/audit-scope-change.dto';

@Injectable({
  providedIn: 'root',
})
export class AudtiNewService {
  private _audits$ = new BehaviorSubject<Audit[]>(null);
  get audits$(): Observable<Audit[]> {
    return this._audits$.asObservable();
  }

  constructor(private http: HttpClient) {}

  getAudits(): void {
    this.http
      .get(environment.baseUrl + 'audits')
      .pipe(
        map((audits: AuditDto[]) => {
          return audits.map<Audit>(auditDto => {
            const endDate = auditDto.endDate ? new Date(auditDto.startDate).getTime() : null;

            return {
              ...auditDto,
              creationDate: new Date(auditDto.creationDate).getTime(),
              startDate: new Date(auditDto.startDate).getTime(),
              endDate,
            };
          });
        }),
      )
      .subscribe(audits => {
        this._audits$.next(audits);
      });
  }

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

  postAudit(audit: Audit): void {
    const auditDto: PostAuditDto = {
      name: audit.name,
      endDate: parseTimestamp(audit.endDate),
      startDate: parseTimestamp(audit.startDate),
      contactPersons: audit.contactPersons?.map(x => x.id) ?? [],
      scope: audit.scope?.map(x => x.id) ?? [],
    };

    this.http
      .post(environment.baseUrl + 'audits', auditDto)
      .pipe(
        map((auditDto: AuditDto) => {
          const endDate = auditDto.endDate ? new Date(auditDto.startDate).getTime() : null;

          return {
            ...auditDto,
            creationDate: new Date(auditDto.creationDate).getTime(),
            startDate: new Date(auditDto.startDate).getTime(),
            endDate,
          };
        }),
      )
      .subscribe(audit => {
        const audits = this._audits$.value;
        this._audits$.next([...audits, audit]);
      });
  }

  putAudit(audit: Audit): void {
    const oldAudit = this._audits$.value.find(a => a.id === audit.id);
    console.log(audit);
    this.putAuditScope(oldAudit, audit);
    this.putAuditContactPersons(oldAudit, audit);

    const putAuditDto: PutAuditDto = {
      name: audit.name,
      status: audit.status,
      endDate: parseTimestamp(audit.endDate),
      startDate: parseTimestamp(audit.startDate),
    };

    this.http
      .put<Audit>(environment.baseUrl + 'audits/' + audit.id, putAuditDto)
      .pipe(
        map(auditDto => {
          const endDate = auditDto.endDate ? new Date(auditDto.startDate).getTime() : null;

          return {
            ...auditDto,
            creationDate: new Date(auditDto.creationDate).getTime(),
            startDate: new Date(auditDto.startDate).getTime(),
            endDate,
            scope: audit.scope,
            contactPersons: audit.contactPersons,
          };
        }),
      )
      .subscribe(audit => {
        console.log(audit);
        const audits = this._audits$.value;
        const indexOfUpdatedAudit = audits.findIndex(a => a.id === audit.id);

        this._audits$.next([
          ...audits.slice(0, indexOfUpdatedAudit),
          audit,
          ...audits.slice(indexOfUpdatedAudit + 1),
        ]);
      });
  }

  /**
   * Comapres the contact persons of an old and an updated audit.
   * Makes PUT and DELETE requests to ../audits/{id}/contactpersons/{id} to update
   * the contact persons of the audit accordingly.
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
   * Comapres the scope of an old and an updated audit.
   * Makes PUT requests to ../audits/{id}/scope to update
   * the added or deleted facCrits.
   */
  private putAuditScope(oldAudit: Audit, newAudit: Audit): void {
    const url = environment.baseUrl + 'audits/' + newAudit.id + '/scope';

    const oldScope = oldAudit.scope;
    const newScope = newAudit.scope;

    for (const facCrit of newScope) {
      const existsInOld = oldAudit.scope.find(x => x.id === facCrit.id);
      if (!existsInOld) {
        const auditScopeChange: AuditScopeChangeDto = {
          facCritId: facCrit.id,
          changeNote: 'Change note for a scope item',
          note: 'Note for a scope item',
          removed: false,
        };

        this.http.put(url, auditScopeChange).subscribe(() => {});
      }
    }

    for (const facCrit of oldScope) {
      const existsInNew = newAudit.scope.find(x => x.id === facCrit.id);
      if (!existsInNew) {
        const auditScopeChange: AuditScopeChangeDto = {
          facCritId: facCrit.id,
          changeNote: 'Change note for a scope item',
          note: 'Note for a scope item',
          removed: true,
        };
        console.log(facCrit.id);
        this.http.put(url, auditScopeChange).subscribe(() => {});
      }
    }
  }
}
