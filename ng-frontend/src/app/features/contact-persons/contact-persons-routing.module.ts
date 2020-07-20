import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ContactPersonsComponent } from './contact-persons.component';
import { AddContactPersonDialogComponent } from './add-contact-person-dialog/add-contact-person-dialog.component';
import { EditContactPersonDialogComponent } from './edit-contact-person-dialog/edit-contact-person-dialog.component';

const routes: Routes = [
  {
    path: '',
    component: ContactPersonsComponent,
    children: [
      {
        path: 'new',
        component: AddContactPersonDialogComponent,
      },
      {
        path: ':id/edit',
        component: EditContactPersonDialogComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ContactPersonsRoutingModule {}
