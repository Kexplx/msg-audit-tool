import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NotFoundComponent } from './shared/components/not-found/not-found.component';
import { SharedModule } from './shared/shared.module';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'audits' },
  {
    path: 'audits',
    loadChildren: () => import('./features/features.module').then(m => m.FeaturesModule),
  },
  { path: '**', component: NotFoundComponent },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes), SharedModule],
  exports: [RouterModule],
})
export class AppRouterModule {}
