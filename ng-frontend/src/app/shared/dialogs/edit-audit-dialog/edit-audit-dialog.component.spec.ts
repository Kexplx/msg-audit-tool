import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditAuditDialogComponent } from './edit-audit-dialog.component';
import { of } from 'rxjs';
import { NbDialogRef } from '@nebular/theme';
import { AppNebularModule } from 'src/app/app-nebular.module';
import { NgxsModule } from '@ngxs/store';
import { Router } from '@angular/router';

describe('EditAuditDialogComponent', () => {
  let component: EditAuditDialogComponent;
  let fixture: ComponentFixture<EditAuditDialogComponent>;

  beforeEach(() => {
    const nbDialogRefStub = {
      onClose: of(true),
      close: () => {},
      componentRef: { instance: { id: '123' } },
    };

    const routerStub = {
      navigate: () => of(true).toPromise(),
    };

    TestBed.configureTestingModule({
      declarations: [EditAuditDialogComponent],
      imports: [AppNebularModule, NgxsModule.forRoot([])],
      providers: [
        { provide: NbDialogRef, useValue: nbDialogRefStub },
        { provide: Router, useValue: routerStub },
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
