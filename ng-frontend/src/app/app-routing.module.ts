import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NotFoundComponent } from './core/not-found/not-found.component';
import { SharedModule } from './shared/shared.module';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'audits' },
  {
    path: 'audits',
    loadChildren: () => import('./features/audits/audits.module').then(m => m.AuditsModule),
  },
  {
    path: 'audits/:auditId',
    loadChildren: () =>
      import('./features/audit-overview/audit-overview.module').then(m => m.AuditOverviewModule),
  },
  {
    path: 'contact-persons',
    loadChildren: () =>
      import('./features/contact-persons/contact-persons.module').then(m => m.ContactPersonsModule),
  },
  {
    path: 'audits/:auditId/interviews/:interviewId/:facCritId',
    loadChildren: () =>
      import('./features/interview/interview.module').then(m => m.InterviewModule),
  },
  { path: '**', component: NotFoundComponent },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes), SharedModule],
  exports: [RouterModule],
})
export class AppRouterModule {}
