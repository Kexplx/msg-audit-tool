import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmDiscardDialogComponent } from './confirm-discard-dialog.component';
import { of } from 'rxjs';
import { NbDialogRef } from '@nebular/theme';
import { SharedModule } from 'src/app/shared/shared.module';
import { ContactPersonService } from 'src/app/core/http/contact-person.service';
import {
  contactPersonServiceSpy,
  interviewServiceSpy,
  questionServiceSpy,
  facCritServiceSpy,
  auditServiceSpy,
} from 'src/app/core/ngxs/test/service-spies';
import { InterviewService } from 'src/app/core/http/interview.service';
import { QuestionService } from 'src/app/core/http/question.service';
import { FacCritService } from 'src/app/core/http/facCrit.service';
import { AuditService } from 'src/app/core/http/audit.service';

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
        { provide: QuestionService, useValue: questionServiceSpy },
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
