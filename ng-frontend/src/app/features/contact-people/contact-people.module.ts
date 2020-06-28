import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ContactPeopleRoutingModule } from './contact-people-routing.module';
import { ContactPeopleListComponent } from './components/contact-people-list/contact-people-list.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { ContactPersonCardComponent } from './components/contact-people-list/contact-person-card/contact-person-card.component';

@NgModule({
  declarations: [ContactPeopleListComponent, ContactPersonCardComponent],
  imports: [CommonModule, ContactPeopleRoutingModule, SharedModule],
})
export class ContactPeopleModule {}
