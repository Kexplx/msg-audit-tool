import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NebularModule } from './nebular/nebular.module';
import { AddAuditDialogComponent } from './components/dialogs/add-audit-dialog/add-audit-dialog.component';
import { EditAuditDialogComponent } from './components/dialogs/edit-audit-dialog/edit-audit-dialog.component';
import { ConfirmDiscardDialogComponent } from './components/dialogs/confirm-discard-dialog/confirm-discard-dialog.component';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AddContactPersonDialogComponent } from './components/dialogs/add-contact-person-dialog/add-contact-person-dialog.component';
import { ContactPersonFormComponent } from './components/forms/contact-person-form/contact-person-form.component';
import { EditContactPersonDialogComponent } from './components/dialogs/edit-contact-person-dialog/edit-contact-person-dialog.component';
import { AuditFormComponent } from './components/forms/audit-form/audit-form.component';
import { FactorsPipe } from './pipes/factors.pipe';
import { CriteriasPipe } from './pipes/criterias.pipe';
import { InterviewFormComponent } from './components/forms/interview-form/interview-form.component';
import { AddInterviewDialogComponent } from './components/dialogs/add-interview-dialog/add-interview-dialog.component';

@NgModule({
  declarations: [
    AddAuditDialogComponent,
    EditAuditDialogComponent,
    AddContactPersonDialogComponent,
    EditContactPersonDialogComponent,
    AddInterviewDialogComponent,
    AuditFormComponent,
    ContactPersonFormComponent,
    InterviewFormComponent,
    ConfirmDiscardDialogComponent,
    FactorsPipe,
    CriteriasPipe,
  ],
  imports: [CommonModule, RouterModule, NebularModule, ReactiveFormsModule],
  exports: [NebularModule, FactorsPipe],
})
export class SharedModule {}
