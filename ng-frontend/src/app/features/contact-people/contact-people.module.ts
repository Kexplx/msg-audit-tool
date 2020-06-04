import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ContactPeopleRoutingModule } from './contact-people-routing.module';
import { ContactPeopleListComponent } from './components/contact-people-list/contact-people-list.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { CoreModule } from 'src/app/core/core.module';

@NgModule({
  declarations: [ContactPeopleListComponent],
  imports: [CommonModule, ContactPeopleRoutingModule, SharedModule],
})
export class ContactPeopleModule {}
