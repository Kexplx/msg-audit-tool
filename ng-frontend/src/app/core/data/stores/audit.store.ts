import { Injectable } from '@angular/core';
import { Audit } from '../models/audit.model';
import { Observable, BehaviorSubject } from 'rxjs';
import { AuditService } from '../http/audit.service';
import { StoreActionService } from './store-action.service';

@Injectable({
  providedIn: 'root',
})
export class AuditStore {
  private _audits$ = new BehaviorSubject<Audit[]>(null);
  get audits$(): Observable<Audit[]> {
    return this._audits$.asObservable();
  }

  constructor(private auditService: AuditService, private storeActionService: StoreActionService) {}

  loadAudits(): void {
    this.auditService.getAudits().subscribe(audits => {
      this._audits$.next(audits);

      this.storeActionService.notifyLoad('Audits wurden geladen.');
    });
  }

  addAudit(audit: Audit): void {
    this.auditService.postAudit(audit).subscribe(audit => {
      const audits = this._audits$.value;

      if (!audits) {
        this._audits$.next([audit]);
      } else {
        this._audits$.next([...audits, audit]);
      }

      this.storeActionService.notifyAdd('Audit wurde erstellt.');
    });
  }

  updateAudit(audit: Audit): void {
    const oldAudit = this._audits$.value?.find(a => a.id === audit.id);

    this.auditService.putAudit(oldAudit, audit).subscribe(audit => {
      const audits = this._audits$.value;
      const indexOfUpdatedAudit = audits.findIndex(a => a.id === audit.id);

      this._audits$.next([
        ...audits.slice(0, indexOfUpdatedAudit),
        audit,
        ...audits.slice(indexOfUpdatedAudit + 1),
      ]);

      this.storeActionService.notifyEdit('Audit wurde aktualisiert.');
    });
  }
}
