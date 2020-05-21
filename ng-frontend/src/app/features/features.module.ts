import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuditListComponent } from './audit-list/audit-list.component';
import { AuditCardComponent } from './audit-list/audit-card/audit-card.component';
import { AuditOverviewComponent } from './interview/audit-overview/audit-overview.component';
import { FeaturesRoutingModule } from './features-routing.module';
import { CategoryCardComponent } from './interview/audit-overview/category-card/category-card.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  imports: [CommonModule, SharedModule, FeaturesRoutingModule],
  declarations: [
    AuditListComponent,
    AuditCardComponent,
    CategoryCardComponent,
    AuditOverviewComponent,
  ],
})
export class FeaturesModule {}
