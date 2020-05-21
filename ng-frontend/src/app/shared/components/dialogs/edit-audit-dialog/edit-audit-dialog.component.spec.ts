import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditAuditDialogComponent } from './edit-audit-dialog.component';
import { of } from 'rxjs';
import { NbDialogRef, NbDialogService } from '@nebular/theme';
import { AppNebularModule } from 'src/app/app-nebular.module';
import { NgxsModule } from '@ngxs/store';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';
import { AuditRegistryState } from 'src/app/ngxs/audit-registry.state';

describe('EditAuditDialogComponent', () => {
  let component: EditAuditDialogComponent;
  let fixture: ComponentFixture<EditAuditDialogComponent>;

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

    fixture = TestBed.createComponent(EditAuditDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
