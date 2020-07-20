import { NgModule } from '@angular/core';
import { AuditCardComponent } from './audit-card/audit-card.component';
import { AuditsComponent } from './audits.component';
import { AuditsRoutingModule } from './audits-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { SortAuditPipe } from './sort-audit.pipe';
import { ActiveAuditsPipe } from './active-audits.pipe';
import { ArchivedAuditsPipe } from './archived-audits.pipe';
import { AddAuditDialogComponent } from './add-audit-dialog/add-audit-dialog.component';
import { EditAuditDialogComponent } from './edit-audit-dialog/edit-audit-dialog.component';
import { AuditFormComponent } from './audit-form/audit-form.component';

@NgModule({
  declarations: [
    AuditCardComponent,
    ActiveAuditsPipe,
    ArchivedAuditsPipe,
    SortAuditPipe,
    AddAuditDialogComponent,
    EditAuditDialogComponent,
    AuditFormComponent,
    AuditsComponent,
  ],
  imports: [AuditsRoutingModule, SharedModule],
})
export class AuditsModule {}
