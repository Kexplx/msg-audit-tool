import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuditListComponent } from './audit-list/audit-list.component';
import { AddAuditComponent } from './add-audit/add-audit.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'audits' },
  { path: 'audits', component: AuditListComponent, children: [] },
  { path: 'audits/add', component: AddAuditComponent },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRouterModule {}