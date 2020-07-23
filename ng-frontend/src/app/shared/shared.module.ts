import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NebularModule } from './nebular/nebular.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { FactorsPipe } from './pipes/factors.pipe';
import { CriteriasPipe } from './pipes/criterias.pipe';
import { ConfirmDiscardDialogComponent } from './components/dialogs/confirm-discard-dialog/confirm-discard-dialog.component';
import { ContactPersonSearchComponent } from './components/contact-person-search/contact-person-search.component';

@NgModule({
  declarations: [
    FactorsPipe,
    CriteriasPipe,
    ConfirmDiscardDialogComponent,
    ContactPersonSearchComponent,
  ],
  imports: [CommonModule, RouterModule, NebularModule],
  exports: [
    NebularModule,
    CriteriasPipe,
    ContactPersonSearchComponent,
    FactorsPipe,
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
  ],
})
export class SharedModule {}
