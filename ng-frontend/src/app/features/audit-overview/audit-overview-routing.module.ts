import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuditOverviewComponent } from './audit-overview.component';
import { AddInterviewDialogComponent } from '../../shared/components/dialogs/add-interview-dialog/add-interview-dialog.component';
import { InterviewListComponent } from './interview-list/interview-list.component';
import { AuditInfoComponent } from './audit-info/audit-info.component';
import { EditAuditDialogComponent } from 'src/app/shared/components/dialogs/edit-audit-dialog/edit-audit-dialog.component';

const routes: Routes = [
  {
    path: '',
    component: AuditOverviewComponent,
    children: [
      {
        path: '',
        redirectTo: 'interviews',
        pathMatch: 'full',
      },
      {
        path: 'interviews',
        component: InterviewListComponent,
        children: [{ path: 'new', component: AddInterviewDialogComponent }],
      },
      {
        path: 'contact-persons',
        component: AuditInfoComponent,
        children: [{ path: 'edit', component: EditAuditDialogComponent }],
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AuditOverviewRoutingModule {}
