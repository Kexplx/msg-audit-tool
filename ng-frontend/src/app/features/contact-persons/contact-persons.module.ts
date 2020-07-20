import { NgModule } from '@angular/core';

import { ContactPersonsRoutingModule } from './contact-persons-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { ContactPersonCardComponent } from './contact-person-card/contact-person-card.component';
import { ContactPersonsComponent } from './contact-persons.component';
import { EditContactPersonDialogComponent } from './edit-contact-person-dialog/edit-contact-person-dialog.component';
import { AddContactPersonDialogComponent } from './add-contact-person-dialog/add-contact-person-dialog.component';
import { ContactPersonFormComponent } from './contact-person-form/contact-person-form.component';

@NgModule({
  declarations: [
    ContactPersonsComponent,
    EditContactPersonDialogComponent,
    AddContactPersonDialogComponent,
    ContactPersonFormComponent,
    ContactPersonCardComponent,
  ],
  imports: [ContactPersonsRoutingModule, SharedModule],
})
export class ContactPersonsModule {}
