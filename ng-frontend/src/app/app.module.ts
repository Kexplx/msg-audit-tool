import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppNebularModule } from './app-nebular.module';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { AuditListComponent } from './audit-list/audit-list.component';
import { AuditCardComponent } from './audit-list/audit-card/audit-card.component';
import { AppRouterModule } from './app-routing.module';
import { AddAuditDialogComponent } from './audit-list/add-audit-dialog/add-audit-dialog.component';
import { ErrorComponent } from './error/error.component';

@NgModule({
  declarations: [
    AppComponent,
    AddAuditDialogComponent,
    NavbarComponent,
    AuditListComponent,
    AuditCardComponent,
    ErrorComponent,
  ],
  imports: [BrowserModule, BrowserAnimationsModule, AppNebularModule, AppRouterModule],
  bootstrap: [AppComponent],
})
export class AppModule {}
