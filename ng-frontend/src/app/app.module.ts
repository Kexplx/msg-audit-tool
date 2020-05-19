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
import { ActionListenerDirective } from './shared/directives/action-listener.directive';
import { EditAuditDialogComponent } from './shared/dialogs/edit-audit-dialog/edit-audit-dialog.component';
import { AuditDataFormComponent } from './shared/forms/audit-data/audit-data-form.component';
import { AddAuditDialogComponent } from './shared/dialogs/add-audit-dialog/add-audit-dialog.component';
import { ConfirmDiscardDialogComponent } from './shared/dialogs/confirm-discard-dialog/confirm-discard-dialog.component';
import { SortAuditPipe } from './pipes/sort-audit.pipe';
import { AuditOverviewComponent } from './interview/audit-overview/audit-overview.component';
import { CategoryCardComponent } from './interview/audit-overview/category-card/category-card.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    AuditListComponent,
    AddAuditDialogComponent,
    AuditCardComponent,
    EditAuditDialogComponent,
    AuditOverviewComponent,
    ActionListenerDirective,
    CategoryCardComponent,
    AuditDataFormComponent,
    NotFoundComponent,
    ConfirmDiscardDialogComponent,
    SortAuditPipe,
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
