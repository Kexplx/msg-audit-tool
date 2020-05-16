import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuditListComponent } from './audit-list/audit-list.component';
import { NotFoundComponent } from './shared/not-found/not-found.component';
import { AddAuditDialogComponent } from './shared/dialogs/add-audit-dialog/add-audit-dialog.component';
import { EditAuditDialogComponent } from './shared/dialogs/edit-audit-dialog/edit-audit-dialog.component';
import { AuditOverviewComponent } from './interview/audit-overview/audit-overview.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'audits' },
  {
    path: 'audits',
    component: AuditListComponent,
    children: [
      { path: 'new', component: AddAuditDialogComponent },
      { path: ':id/edit', component: EditAuditDialogComponent },
    ],
  },
  {
    path: 'audits/:id',
    component: AuditOverviewComponent,
  },
  { path: '**', component: NotFoundComponent },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRouterModule {}
