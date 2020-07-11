import { Component, Input } from '@angular/core';
import { Audit, AuditStatus } from 'src/app/core/data/models/audit.model';

@Component({
  selector: 'app-audit-card',
  templateUrl: './audit-card.component.html',
  styleUrls: ['./audit-card.component.scss'],
})
export class AuditCardComponent {
  @Input() audit: Audit;
  auditStatus = AuditStatus;

  get contactPerson() {
    return this.audit.contactPersons ? this.audit.contactPersons[0] : null;
  }
}
