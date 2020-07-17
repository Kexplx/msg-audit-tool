import { Pipe, PipeTransform } from '@angular/core';
import { Audit, AuditStatus } from 'src/app/core/data/models/audit.model';

@Pipe({
  name: 'activeAudits',
})
export class ActiveAuditsPipe implements PipeTransform {
  /**
   * Returns all audits with an active or planned status.
   * If no audits have those status it returns null.
   */
  transform(audits: Audit[]): Audit[] | null {
    const result = audits.filter(
      a => a.status === AuditStatus.Active || a.status === AuditStatus.Planned,
    );

    return result.length > 0 ? result : null;
  }
}
