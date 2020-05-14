import { Pipe, PipeTransform } from '@angular/core';
import { Audit } from '../data/models/audit.model';

@Pipe({
  name: 'sortAudit'
})
export class SortAuditPipe implements PipeTransform {

  transform(audits: Audit[]): Audit[] {
    audits.sort((a, b) => {
      return b.creationDate - a.creationDate;
    });
    return audits;
  }

}
