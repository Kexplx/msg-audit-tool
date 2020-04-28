import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppNebularModule } from './app-nebular.module';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { ProjectListComponent } from './project-list/project-list.component';
import { ProjectCardComponent } from './project-list/project-card/project-card.component';
import { AppRouterModule } from './app-routing.module';

@NgModule({
  declarations: [AppComponent, NavbarComponent, ProjectListComponent, ProjectCardComponent],
  imports: [BrowserModule, BrowserAnimationsModule, AppNebularModule, AppRouterModule],
  bootstrap: [AppComponent],
})
export class AppModule {}
