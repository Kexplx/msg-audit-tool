import { Injectable } from '@angular/core';
import { Audit } from '../data/models/audit.model';
import { Observable, BehaviorSubject } from 'rxjs';
import { AuditService } from '../http/audit.service';

@Injectable({
  providedIn: 'root',
})
export class AuditStore {
  private _audits$ = new BehaviorSubject<Audit[]>(null);
  get audits$(): Observable<Audit[]> {
    return this._audits$.asObservable();
  }

  constructor(private auditService: AuditService) {}

  loadAudits(): void {
    this.auditService.getAudits().subscribe(audits => {
      this._audits$.next(audits);
    });
  }

  addAudit(audit: Audit): void {
    this.auditService.postAudit(audit).subscribe(audit => {
      const audits = this._audits$.value;
      this._audits$.next([...audits, audit]);
    });
  }

  updateAudit(audit: Audit): void {
    const oldAudit = this._audits$.value.find(a => a.id === audit.id);
    this.auditService.putAudit(oldAudit, audit).subscribe(audit => {
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
}
