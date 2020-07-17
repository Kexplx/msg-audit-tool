import { Pipe, PipeTransform } from '@angular/core';
import { Audit, AuditStatus } from 'src/app/core/data/models/audit.model';

@Pipe({
  name: 'archivedAudits',
})
export class ArchivedAuditsPipe implements PipeTransform {
  /**
   * Returns all audits with a finished or cancelled status.
   * If no audits have those status it returns null.
   */
  transform(audits: Audit[]): Audit[] | null {
    const result = audits.filter(
      a => a.status === AuditStatus.Finished || a.status === AuditStatus.Cancelled,
    );

    return result.length > 0 ? result : null;
  }
}
