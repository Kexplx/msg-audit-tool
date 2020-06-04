import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ContactPeopleListComponent } from './components/contact-people-list/contact-people-list.component';
import { AddContactPersonDialogComponent } from 'src/app/shared/components/dialogs/add-contact-person-dialog/add-contact-person-dialog.component';

const routes: Routes = [
  {
    path: '',
    component: ContactPeopleListComponent,
    children: [
      {
        path: 'new',
        component: AddContactPersonDialogComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ContactPeopleRoutingModule {}
