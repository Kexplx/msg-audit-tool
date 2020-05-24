import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuditOverviewRoutingModule } from './audit-overview-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { AuditOverviewComponent } from './components/audit-overview/audit-overview.component';

@NgModule({
  declarations: [AuditOverviewComponent],
  imports: [CommonModule, AuditOverviewRoutingModule, SharedModule],
})
export class AuditOverviewModule {}
