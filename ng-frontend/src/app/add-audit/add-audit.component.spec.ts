import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAuditComponent } from './add-audit.component';
import { NgxsModule, Store } from '@ngxs/store';
import { AuditRegistryState } from '../ngxs/audit-registry.state';
import { Router, RouterModule } from '@angular/router';

describe('AddAuditComponent', () => {
  let component: AddAuditComponent;
  let fixture: ComponentFixture<AddAuditComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddAuditComponent],
      imports: [NgxsModule.forRoot([AuditRegistryState]), RouterModule.forRoot([])],
    });

    fixture = TestBed.createComponent(AddAuditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
