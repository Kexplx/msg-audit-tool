import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuditOverviewRoutingModule } from './audit-overview-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { AuditOverviewComponent } from './components/audit-overview/audit-overview.component';
import { CategoryCardComponent } from './components/category-card/category-card.component';

@NgModule({
  declarations: [AuditOverviewComponent, CategoryCardComponent],
  imports: [CommonModule, AuditOverviewRoutingModule, SharedModule],
})
export class AuditOverviewModule {}
