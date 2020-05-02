import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuditListComponent } from './audit-list/audit-list.component';
import { ErrorComponent } from './error/error.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'audits' },
  { path: 'audits', component: AuditListComponent },
  { path: '**', component: ErrorComponent }
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRouterModule {}
