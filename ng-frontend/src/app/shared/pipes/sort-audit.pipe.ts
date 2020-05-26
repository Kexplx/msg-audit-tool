import { Pipe, PipeTransform } from '@angular/core';
import { Audit } from 'src/app/core/data/models/audit.model';

@Pipe({
  name: 'sortAudit',
})
export class SortAuditPipe implements PipeTransform {
  transform(audits: Audit[]): Audit[] {
    return audits.sort((a, b) => b.creationDate - a.creationDate);
  }
}
