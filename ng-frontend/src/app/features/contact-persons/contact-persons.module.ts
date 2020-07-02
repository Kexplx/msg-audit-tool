import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ContactPersonsRoutingModule } from './contact-persons-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { ContactPersonCardComponent } from './components/contact-persons-list/contact-person-card/contact-person-card.component';
import { ContactPersonsListComponent } from './components/contact-persons-list/contact-persons-list.component';

@NgModule({
  declarations: [ContactPersonsListComponent, ContactPersonCardComponent],
  imports: [CommonModule, ContactPersonsRoutingModule, SharedModule],
})
export class ContactPersonsModule {}
