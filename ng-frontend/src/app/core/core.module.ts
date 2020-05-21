import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgxsModule } from '@ngxs/store';
import { AuditRegistryState } from './ngxs/audit-registry.state';

@NgModule({
  imports: [NgxsModule.forRoot([AuditRegistryState])],
  exports: [NgxsModule],
})
export class CoreModule {}
