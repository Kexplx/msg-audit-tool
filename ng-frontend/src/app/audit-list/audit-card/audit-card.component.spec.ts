import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditCardComponent } from './audit-card.component';
import { Audit, AuditStatus } from 'src/app/data/models/audit.model';
import { AppNebularModule } from 'src/app/app-nebular.module';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DebugElement } from '@angular/core';
import { NgxsModule } from '@ngxs/store';
import { AuditRegistryState } from 'src/app/ngxs/audit-registry.state';
import * as jasmine from 'karma-jasmine';

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
    status: AuditStatus.IsPlanned,
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

  it('should render the company name into nb-accordion-item-header-wrapper and nb-accordion-item-body', () => {
    const de: DebugElement = fixture.debugElement;
    const el: HTMLElement = de.nativeElement.querySelector('.nb-accordion-item-header-wrapper');
    expect(el.innerHTML).toContain(audit.customerData.name);
  });

  it('should render audit status tag with default case IsPlanned and change its statuses correctly', () => {
    expect(
      fixture.debugElement.nativeElement.querySelector('nb-badge').getAttribute('text'),
    ).toContain('Geplant');
    component.audit.status = AuditStatus.InAction;
    fixture.detectChanges();
    expect(
      fixture.debugElement.nativeElement.querySelector('nb-badge').getAttribute('text'),
    ).toContain('In Bearbeitung');
    component.audit.status = AuditStatus.IsFinished;
    fixture.detectChanges();
    expect(
      fixture.debugElement.nativeElement.querySelector('nb-badge').getAttribute('text'),
    ).toContain('Abgeschlossen');
    component.audit.status = AuditStatus.IsCanceled;
    fixture.detectChanges();
    expect(
      fixture.debugElement.nativeElement.querySelector('nb-badge').getAttribute('text'),
    ).toContain('Abgebrochen');
  });
});
