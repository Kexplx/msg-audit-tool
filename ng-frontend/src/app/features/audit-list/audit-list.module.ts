import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuditCardComponent } from './audit-card/audit-card.component';
import { AuditListComponent } from './audit-list.component';
import { AuditListRoutingModule } from './audit-list-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { SortAuditPipe } from './sort-audit.pipe';

@NgModule({
  declarations: [AuditCardComponent, SortAuditPipe, AuditListComponent],
  imports: [CommonModule, AuditListRoutingModule, SharedModule],
})
export class AuditListModule {}
