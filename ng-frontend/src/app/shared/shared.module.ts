import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NebularModule } from './nebular/nebular.module';
import { NavbarComponent } from './components/navbar/navbar.component';
import { AddAuditDialogComponent } from './components/dialogs/add-audit-dialog/add-audit-dialog.component';
import { EditAuditDialogComponent } from './components/dialogs/edit-audit-dialog/edit-audit-dialog.component';
import { ActionListenerDirective } from './directives/action-listener.directive';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { ConfirmDiscardDialogComponent } from './components/dialogs/confirm-discard-dialog/confirm-discard-dialog.component';
import { SortAuditPipe } from './pipes/sort-audit.pipe';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AddContactPersonDialogComponent } from './components/dialogs/add-contact-person-dialog/add-contact-person-dialog.component';
import { ContactPersonFormComponent } from './components/forms/contact-person-form/contact-person-form.component';
import { EditContactPersonDialogComponent } from './components/dialogs/edit-contact-person-dialog/edit-contact-person-dialog.component';
import { FactorsPipe } from './pipes/factors.pipe';
import { CriteriasPipe } from './pipes/criterias.pipe';
import { AuditFormComponent } from './components/forms/audit-form/audit-form.component';
import { FactorByIdPipe } from './pipes/factorById.pipe';

@NgModule({
  declarations: [
    NavbarComponent,
    AddAuditDialogComponent,
    EditAuditDialogComponent,
    ActionListenerDirective,
    CriteriasPipe,
    FactorsPipe,
    AddContactPersonDialogComponent,
    FactorByIdPipe,
    ContactPersonFormComponent,
    EditContactPersonDialogComponent,
    AuditFormComponent,
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
    AddContactPersonDialogComponent,
    FactorsPipe,
    CriteriasPipe,
    FactorByIdPipe,
    ActionListenerDirective,
    AuditFormComponent,
    NotFoundComponent,
    ContactPersonFormComponent,
    ConfirmDiscardDialogComponent,
    EditContactPersonDialogComponent,
    SortAuditPipe,
  ],
})
export class SharedModule {}
