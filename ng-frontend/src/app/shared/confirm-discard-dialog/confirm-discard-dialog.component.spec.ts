import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmDiscardDialogComponent } from './confirm-discard-dialog.component';
import { of } from 'rxjs';
import { NbDialogRef } from '@nebular/theme';
import { AppNebularModule } from 'src/app/app-nebular.module';

describe('ConfirmDiscardDialogComponent', () => {
  let component: ConfirmDiscardDialogComponent;
  let fixture: ComponentFixture<ConfirmDiscardDialogComponent>;
  const nbDialogRefStub: Partial<NbDialogRef<any>> = {
    onClose: of(true),
    close: () => {},
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConfirmDiscardDialogComponent],
      imports: [AppNebularModule],
      providers: [{ provide: NbDialogRef, useValue: nbDialogRefStub }],
    });

    fixture = TestBed.createComponent(ConfirmDiscardDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
