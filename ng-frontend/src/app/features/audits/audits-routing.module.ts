import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuditsComponent } from './audits.component';
import { AddAuditDialogComponent } from './add-audit-dialog/add-audit-dialog.component';
import { EditAuditDialogComponent } from './edit-audit-dialog/edit-audit-dialog.component';

const routes: Routes = [
  {
    path: '',
    component: AuditsComponent,
    children: [
      { path: 'new', component: AddAuditDialogComponent },
      { path: ':id/edit', component: EditAuditDialogComponent },
    ],
  },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AuditsRoutingModule {}
