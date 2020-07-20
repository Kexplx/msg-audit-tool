import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmDiscardDialogComponent } from './confirm-discard-dialog.component';
import { of } from 'rxjs';
import { NbDialogRef } from '@nebular/theme';
import { SharedModule } from 'src/app/shared/shared.module';
import { ContactPersonService } from 'src/app/core/data/http/contact-person.service';
import { InterviewService } from 'src/app/core/data/http/interview.service';
import { QuestionService } from 'src/app/core/data/http/question.service';
import { FacCritService } from 'src/app/core/data/http/facCrit.service';
import { AuditService } from 'src/app/core/data/http/audit.service';
import { contactPersonServiceSpy } from 'src/app/core/data/http/test/spies/contact-person.service.spy';
import { interviewServiceSpy } from 'src/app/core/data/http/test/spies/interview.service.spy';
import { facCritServiceSpy } from 'src/app/core/data/http/test/spies/faccrit.service.spy';
import { auditServiceSpy } from 'src/app/core/data/http/test/spies/audit.service.spy';

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
      imports: [SharedModule],
      providers: [
        { provide: NbDialogRef, useValue: nbDialogRefStub },
        { provide: ContactPersonService, useValue: contactPersonServiceSpy },
        { provide: InterviewService, useValue: interviewServiceSpy },
        { provide: FacCritService, useValue: facCritServiceSpy },
        { provide: AuditService, useValue: auditServiceSpy },
      ],
    });

    fixture = TestBed.createComponent(ConfirmDiscardDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
