import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NbThemeModule, NbLayoutModule } from '@nebular/theme';
import { NbEvaIconsModule } from '@nebular/eva-icons';
import { NebularModule } from './nebular/nebular.module';
import { RouterModule, Routes } from '@angular/router';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { ProjectListComponent } from './project-list/project-list.component';
import { ProjectCardComponent } from './project-list/project-card/project-card.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'projekte' },
  { path: 'projekte', component: ProjectListComponent },
];

@NgModule({
  declarations: [AppComponent, NavbarComponent, ProjectListComponent, ProjectCardComponent],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    NbThemeModule.forRoot({ name: 'default' }),
    NbLayoutModule,
    NbEvaIconsModule,
    RouterModule.forRoot(routes),
    NebularModule,
  ],
  providers: [RouterModule],
  bootstrap: [AppComponent],
})
export class AppModule {}
