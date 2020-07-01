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
   * Sends a GET to ../audits
   */
  // getAudits() {
  //   return this.http.get<AuditDto[]>(compileTimeSwitchedString + '/audits').pipe(
  //     map(audits => {
  //       const result = audits.map<Audit>(auditDto => {
  //         const endDate = auditDto.endDate ? new Date(auditDto.startDate) : null;

  //         return { creationDate: new Date(auditDto.creationDate).getTime(),
  //         startDate: new Date(auditDto.startDate).getTime(),
  //         endDate,
  //         id: String(auditDto.auditId),

  //        };
  //       });
  //       return audits;
  //     }),
  //   );
  // }
}
