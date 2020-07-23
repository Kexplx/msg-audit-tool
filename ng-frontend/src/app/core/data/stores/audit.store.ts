import { Injectable } from '@angular/core';
import { Audit } from '../models/audit.model';
import { Observable, BehaviorSubject } from 'rxjs';
import { AuditService } from '../http/audit.service';
import { StoreActionService, StoreActionType } from './store-action.service';

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

      this.storeActionService.notify({
        message: 'Audits wurden geladen.',
        type: StoreActionType.Load,
      });
    });
  }

  addAudit(audit: Audit): void {
    this.auditService.postAudit(audit).subscribe(audit => {
      const audits = this._audits$.value;

      this.storeActionService.notify({
        message: 'Audit wurde erstellt.',
        type: StoreActionType.Add,
      });

      if (!audits) {
        return this._audits$.next([audit]);
      }

      this._audits$.next([...audits, audit]);
    });
  }

  updateAudit(audit: Audit): void {
    const oldAudit = this._audits$.value?.find(a => a.id === audit.id);

    if (!oldAudit) {
      throw new Error("Couldn't find audit to update");
    }

    this.auditService.putAudit(oldAudit, audit).subscribe(audit => {
      const audits = this._audits$.value;
      const indexOfUpdatedAudit = audits.findIndex(a => a.id === audit.id);

      this._audits$.next([
        ...audits.slice(0, indexOfUpdatedAudit),
        audit,
        ...audits.slice(indexOfUpdatedAudit + 1),
      ]);

      this.storeActionService.notify({
        message: 'Audit wurde aktualisiert.',
        type: StoreActionType.Edit,
      });
    });
  }
}
