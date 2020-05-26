import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditAuditDialogComponent } from './edit-audit-dialog.component';
import { of } from 'rxjs';
import { NbDialogRef, NbDialogService } from '@nebular/theme';
import { RouterTestingModule } from '@angular/router/testing';
import { SharedModule } from 'src/app/shared/shared.module';
import { CoreModule } from 'src/app/core/core.module';

describe('EditAuditDialogComponent', () => {
  let component: EditAuditDialogComponent;
  let fixture: ComponentFixture<EditAuditDialogComponent>;

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

    fixture = TestBed.createComponent(EditAuditDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
