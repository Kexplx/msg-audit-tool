import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuditOverviewComponent } from './components/audit-overview/audit-overview.component';
import { EditAuditDialogComponent } from 'src/app/shared/components/dialogs/edit-audit-dialog/edit-audit-dialog.component';

const routes: Routes = [
  {
    path: '',
    component: AuditOverviewComponent,
    children: [{ path: 'edit', component: EditAuditDialogComponent }],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AuditOverviewRoutingModule {}
