import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuditOverviewComponent } from './components/audit-overview/audit-overview.component';
import { NewInterviewDialogComponent } from './components/audit-overview/interview-list/new-interview-dialog/new-interview-dialog.component';
import { InterviewListComponent } from './components/audit-overview/interview-list/interview-list.component';

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
        children: [{ path: 'new', component: NewInterviewDialogComponent }],
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AuditOverviewRoutingModule {}
