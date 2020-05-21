import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { AddAuditDialogComponent } from 'src/app/shared/components/dialogs/add-audit-dialog/add-audit-dialog.component';
import { EditAuditDialogComponent } from 'src/app/shared/components/dialogs/edit-audit-dialog/edit-audit-dialog.component';
import { AuditListComponent } from './audit-list/audit-list.component';
import { AuditOverviewComponent } from './interview/audit-overview/audit-overview.component';

const routes: Routes = [
  {
    path: '',
    component: AuditListComponent,
    children: [
      { path: 'new', component: AddAuditDialogComponent },
      { path: ':id/edit', component: EditAuditDialogComponent },
    ],
  },
  {
    path: ':id/overview',
    component: AuditOverviewComponent,
    children: [{ path: 'edit', component: EditAuditDialogComponent }],
  },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class FeaturesRoutingModule {}
