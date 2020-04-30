import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppNebularModule } from './app-nebular.module';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { AuditListComponent } from './audit-list/audit-list.component';
import { AuditCardComponent } from './audit-list/audit-card/audit-card.component';
import { AppRouterModule } from './app-routing.module';

@NgModule({
  declarations: [AppComponent, NavbarComponent, AuditListComponent, AuditCardComponent],
  imports: [BrowserModule, BrowserAnimationsModule, AppNebularModule, AppRouterModule],
  bootstrap: [AppComponent],
})
export class AppModule {}
