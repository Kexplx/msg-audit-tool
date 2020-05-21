import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NotFoundComponent } from './shared/components/not-found/not-found.component';
import { SharedModule } from './shared/shared.module';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'audits' },
  {
    path: 'audits',
    // pathMatch: 'full',
    loadChildren: () =>
      import('./features/audit-list/audit-list.module').then(m => m.AuditListModule),
  },
  {
    path: 'audits/:id/overview',
    pathMatch: 'full',
    loadChildren: () =>
      import('./features/audit-overview/audit-overview.module').then(m => m.AuditOverviewModule),
  },
  { path: '**', component: NotFoundComponent },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes), SharedModule],
  exports: [RouterModule],
})
export class AppRouterModule {}
