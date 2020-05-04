import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAuditDialogComponent } from './add-audit-dialog.component';
import { AppNebularModule } from '../app-nebular.module';
import { RouterModule, Router } from '@angular/router';
import { NgxsModule } from '@ngxs/store';
import { AuditRegistryState } from '../ngxs/audit-registry.state';
import { NbDialogRef } from '@nebular/theme';
import { of } from 'rxjs';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';

describe('AddAuditDialogComponent', () => {
  let component: AddAuditDialogComponent;
  let fixture: ComponentFixture<AddAuditDialogComponent>;

  let nbDialogRefStub: Partial<NbDialogRef<any>>;
  let nbRouterStub: Partial<Router>;

  beforeEach(() => {
    nbDialogRefStub = {
      onClose: of(true),
      close: () => {},
    };

    nbRouterStub = {
      navigate: () => of(true).toPromise(),
    };

    TestBed.configureTestingModule({
      declarations: [AddAuditDialogComponent],
      imports: [
        AppNebularModule,
        BrowserAnimationsModule,
        RouterModule.forRoot([]),
        NgxsModule.forRoot([AuditRegistryState]),
        ReactiveFormsModule,
      ],
      providers: [
        { provide: NbDialogRef, useValue: nbDialogRefStub },
        {
          provide: Router,
          useValue: nbRouterStub,
        },
      ],
    });

    fixture = TestBed.createComponent(AddAuditDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
