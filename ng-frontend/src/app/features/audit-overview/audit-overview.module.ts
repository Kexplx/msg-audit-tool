import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuditOverviewRoutingModule } from './audit-overview-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { AuditOverviewComponent } from './audit-overview.component';
import { InterviewCardComponent } from './interview-list/interview-card/interview-card.component';
import { NewInterviewDialogComponent } from '../../shared/components/dialogs/new-interview-dialog/new-interview-dialog.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { InterviewListComponent } from './interview-list/interview-list.component';
import { AuditInfoComponent } from './audit-info/audit-info.component';
import { AuditContactPersonCardComponent } from './audit-info/audit-contact-person-card/contact-person-card.component';
import { CriteriaByFactorIdPipe } from './interview-list/criteria-by-factor-id.pipe';
import { InterviewsByFacCritIdPipe } from './interview-list/interviews-by-fac-crit-id.pipe';

@NgModule({
  declarations: [
    AuditOverviewComponent,
    InterviewCardComponent,
    AuditInfoComponent,
    InterviewListComponent,
    AuditContactPersonCardComponent,
    CriteriaByFactorIdPipe,
    InterviewsByFacCritIdPipe,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    AuditOverviewRoutingModule,
    SharedModule,
  ],
})
export class AuditOverviewModule {}
