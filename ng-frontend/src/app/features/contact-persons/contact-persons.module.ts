import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ContactPersonsRoutingModule } from './contact-persons-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { ContactPersonCardComponent } from './contact-person-card/contact-person-card.component';
import { ContactPersonsComponent } from './contact-persons.component';

@NgModule({
  declarations: [ContactPersonsComponent, ContactPersonCardComponent],
  imports: [CommonModule, ContactPersonsRoutingModule, SharedModule],
})
export class ContactPersonsModule {}
