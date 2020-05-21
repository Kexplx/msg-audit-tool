import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAuditDialogComponent } from './add-audit-dialog.component';
import { RouterModule, Router } from '@angular/router';
import { NgxsModule } from '@ngxs/store';
import { NbDialogRef, NbDialogService } from '@nebular/theme';
import { of } from 'rxjs';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { AppNebularModule } from 'src/app/app-nebular.module';
import { AuditRegistryState } from 'src/app/ngxs/audit-registry.state';
import { EditAuditDialogComponent } from '../edit-audit-dialog/edit-audit-dialog.component';
import { RouterTestingModule } from '@angular/router/testing';

describe('AddAuditDialogComponent', () => {
  let component: AddAuditDialogComponent;
  let fixture: ComponentFixture<AddAuditDialogComponent>;

  let nbDialogRefStub: Partial<NbDialogRef<any>>;
  let nbRouterStub: Partial<Router>;

  beforeEach(() => {
    const nbDialogRefStub: Partial<NbDialogRef<any>> = {
      onClose: of(true),
      close: () => {},
    };

    const nbDialogServiceStub = {
      open: (...k: any) => nbDialogRefStub,
    };

    TestBed.configureTestingModule({
      declarations: [EditAuditDialogComponent],
      imports: [AppNebularModule, NgxsModule.forRoot([AuditRegistryState]), RouterTestingModule],
      providers: [
        { provide: NbDialogRef, useValue: nbDialogRefStub },
        { provide: NbDialogService, useValue: nbDialogServiceStub },
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
