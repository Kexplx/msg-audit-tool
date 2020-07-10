import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuditCardComponent } from './audit-card/audit-card.component';
import { AuditsComponent } from './audits.component';
import { AuditsRoutingModule } from './audits-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { SortAuditPipe } from './sort-audit.pipe';

@NgModule({
  declarations: [AuditCardComponent, SortAuditPipe, AuditsComponent],
  imports: [CommonModule, AuditsRoutingModule, SharedModule],
})
export class AuditsModule {}
