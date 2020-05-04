import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppNebularModule } from './app-nebular.module';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { AuditListComponent } from './audit-list/audit-list.component';
import { AuditCardComponent } from './audit-list/audit-card/audit-card.component';
import { AppRouterModule } from './app-routing.module';
import { NotFoundComponent } from './shared/not-found/not-found.component';
import { ReactiveFormsModule } from '@angular/forms';
import { NgxsModule } from '@ngxs/store';
import { NgxsReduxDevtoolsPluginModule } from '@ngxs/devtools-plugin';
import { NgxsLoggerPluginModule } from '@ngxs/logger-plugin';
import { AuditRegistryState } from './ngxs/audit-registry.state';
import { environment } from 'src/environments/environment';
import { AddAuditDialogComponent } from './add-audit-dialog/add-audit-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    AuditListComponent,
    AddAuditDialogComponent,
    AuditCardComponent,
    NotFoundComponent,
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    AppNebularModule,
    AppRouterModule,
    NgxsModule.forRoot([AuditRegistryState], {
      developmentMode: !environment.production,
    }),
    NgxsReduxDevtoolsPluginModule.forRoot(),
    NgxsLoggerPluginModule.forRoot(),
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
