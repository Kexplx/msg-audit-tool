import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NebularModule } from './nebular/nebular.module';
import { NavbarComponent } from './components/navbar/navbar.component';
import { AddAuditDialogComponent } from './components/dialogs/add-audit-dialog/add-audit-dialog.component';
import { EditAuditDialogComponent } from './components/dialogs/edit-audit-dialog/edit-audit-dialog.component';
import { ActionListenerDirective } from './directives/action-listener.directive';
import { AuditDataFormComponent } from './components/forms/audit-data/audit-data-form.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { ConfirmDiscardDialogComponent } from './components/dialogs/confirm-discard-dialog/confirm-discard-dialog.component';
import { SortAuditPipe } from './pipes/sort-audit.pipe';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    NavbarComponent,
    AddAuditDialogComponent,
    EditAuditDialogComponent,
    ActionListenerDirective,
    AuditDataFormComponent,
    NotFoundComponent,
    ConfirmDiscardDialogComponent,
    SortAuditPipe,
  ],
  imports: [CommonModule, RouterModule, NebularModule, ReactiveFormsModule],
  exports: [
    NebularModule,
    NavbarComponent,
    AddAuditDialogComponent,
    EditAuditDialogComponent,
    ActionListenerDirective,
    AuditDataFormComponent,
    NotFoundComponent,
    ConfirmDiscardDialogComponent,
    SortAuditPipe,
  ],
})
export class SharedModule {}
