import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditCardComponent } from './audit-card.component';
import { Audit } from 'src/app/data/models/audit.model';
import { AppNebularModule } from 'src/app/app-nebular.module';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DebugElement } from '@angular/core';
import { NgxsModule } from '@ngxs/store';
import { AuditRegistryState } from 'src/app/ngxs/audit-registry.state';

describe('AuditCardComponent', () => {
  let component: AuditCardComponent;
  let fixture: ComponentFixture<AuditCardComponent>;

  const audit: Audit = {
    name: 'TestAudit',
    contactPerson: {
      firstName: 'Max',
      lastName: 'Meier',
      information: '0192',
      title: 'Herr',
    },
    customerData: {
      department: 'a',
      name: 'b',
      sector: 'c',
    },
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AuditCardComponent],
      imports: [
        RouterModule.forRoot([]),
        AppNebularModule,
        NgxsModule.forRoot([AuditRegistryState]),
        BrowserAnimationsModule,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(AuditCardComponent);
    component = fixture.componentInstance;

    component.audit = audit;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render audit name into nb-accordion-item-header-wrapper', () => {
    const de: DebugElement = fixture.debugElement;
    const el: HTMLElement = de.nativeElement.querySelector('.nb-accordion-item-header-wrapper');
    expect(el.innerHTML).toContain(audit.name);
  });
});
