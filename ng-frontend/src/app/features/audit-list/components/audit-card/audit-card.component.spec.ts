import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditCardComponent } from './audit-card.component';
import { RouterModule } from '@angular/router';
import { DebugElement } from '@angular/core';
import * as jasmine from 'karma-jasmine';
import { Audit, AuditStatus } from 'src/app/core/data/models/audit.model';
import { SharedModule } from 'src/app/shared/shared.module';
import { CoreModule } from 'src/app/core/core.module';

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
      salutation: 'Herr',
    },
    customerData: {
      department: 'a',
      name: 'b',
      sector: 'c',
    },
    status: AuditStatus.Planned,
    creationDate: Date.now(),
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AuditCardComponent],
      imports: [RouterModule.forRoot([]), SharedModule, CoreModule],
    }).compileComponents();

    fixture = TestBed.createComponent(AuditCardComponent);
    component = fixture.componentInstance;

    component.audit = audit;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render audit name into nb-card-header', () => {
    const de: DebugElement = fixture.debugElement;
    const el: HTMLElement = de.nativeElement.querySelector('nb-card-header');
    expect(el.innerHTML).toContain(audit.name);
  });

  it('should render the company name into nb-card-header', () => {
    const de: DebugElement = fixture.debugElement;
    const el: HTMLElement = de.nativeElement.querySelector('nb-card-header');
    expect(el.innerHTML).toContain(audit.customerData.name);
  });

  it('should render audit status tag with default case IsPlanned and change its statuses correctly', () => {
    expect(
      fixture.debugElement.nativeElement
        .querySelector('.banner-status-is-planned')
        .getAttribute('nbPopover'),
    ).toContain('Geplant');
    component.audit.status = AuditStatus.InAction;
    fixture.detectChanges();
    expect(
      fixture.debugElement.nativeElement
        .querySelector('.banner-status-in-action')
        .getAttribute('nbPopover'),
    ).toContain('In Bearbeitung');
    component.audit.status = AuditStatus.Finished;
    fixture.detectChanges();
    expect(
      fixture.debugElement.nativeElement
        .querySelector('.banner-status-is-finished')
        .getAttribute('nbPopover'),
    ).toContain('Abgeschlossen');
    component.audit.status = AuditStatus.Cancelled;
    fixture.detectChanges();
    expect(
      fixture.debugElement.nativeElement
        .querySelector('.banner-status-is-canceled')
        .getAttribute('nbPopover'),
    ).toContain('Abgebrochen');
  });
});