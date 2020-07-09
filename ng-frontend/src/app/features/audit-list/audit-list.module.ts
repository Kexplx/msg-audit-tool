import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuditCardComponent } from './components/audit-list/audit-card/audit-card.component';
import { AuditListComponent } from './components/audit-list/audit-list.component';
import { AuditListRoutingModule } from './audit-list-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  declarations: [AuditCardComponent, AuditListComponent],
  imports: [CommonModule, AuditListRoutingModule, SharedModule],
})
export class AuditListModule {}
