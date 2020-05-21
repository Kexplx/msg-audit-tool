import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAuditDialogComponent } from './add-audit-dialog.component';
import { NbDialogRef, NbDialogService } from '@nebular/theme';
import { of } from 'rxjs';
import { EditAuditDialogComponent } from '../edit-audit-dialog/edit-audit-dialog.component';
import { RouterTestingModule } from '@angular/router/testing';
import { SharedModule } from 'src/app/shared/shared.module';
import { CoreModule } from 'src/app/core/core.module';

describe('AddAuditDialogComponent', () => {
  let component: AddAuditDialogComponent;
  let fixture: ComponentFixture<AddAuditDialogComponent>;

  beforeEach(() => {
    const nbDialogRefStub: Partial<NbDialogRef<any>> = {
      onClose: of(true),
      close: () => {},
    };

    const nbDialogServiceStub = {
      open: () => nbDialogRefStub,
    };

    TestBed.configureTestingModule({
      declarations: [EditAuditDialogComponent],
      imports: [SharedModule, CoreModule, RouterTestingModule],
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
