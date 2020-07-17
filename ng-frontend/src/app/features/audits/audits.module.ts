import { NgModule } from '@angular/core';
import { AuditCardComponent } from './audit-card/audit-card.component';
import { AuditsComponent } from './audits.component';
import { AuditsRoutingModule } from './audits-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { SortAuditPipe } from './sort-audit.pipe';
import { ActiveAuditsPipe } from './active-audits.pipe';
import { ArchivedAuditsPipe } from './archived-audits.pipe';

@NgModule({
  declarations: [
    AuditCardComponent,
    ActiveAuditsPipe,
    ArchivedAuditsPipe,
    SortAuditPipe,
    AuditsComponent,
  ],
  imports: [AuditsRoutingModule, SharedModule],
})
export class AuditsModule {}
