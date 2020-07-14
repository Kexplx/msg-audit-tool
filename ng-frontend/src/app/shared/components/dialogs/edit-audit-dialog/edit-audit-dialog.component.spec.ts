import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditAuditDialogComponent } from './edit-audit-dialog.component';
import { of } from 'rxjs';
import { NbDialogRef, NbDialogService } from '@nebular/theme';
import { RouterTestingModule } from '@angular/router/testing';
import { SharedModule } from 'src/app/shared/shared.module';
import { CoreModule } from 'src/app/core/core.module';
import { Router, RouterModule } from '@angular/router';
import { CONTACTPERSON_DTO_DUMMY } from 'src/app/core/http/test/dummies/contact-persons';
import { ContactPersonService } from 'src/app/core/http/contact-person.service';
import {
  interviewServiceSpy,
  questionServiceSpy,
  facCritServiceSpy,
  auditServiceSpy,
  contactPersonServiceSpy,
} from 'src/app/core/ngxs/test/service-spies';
import { InterviewService } from 'src/app/core/http/interview.service';
import { QuestionService } from 'src/app/core/http/question.service';
import { FacCritService } from 'src/app/core/http/facCrit.service';
import { AuditService } from 'src/app/core/http/audit.service';

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
      imports: [SharedModule, CoreModule, RouterModule.forRoot([])],
      providers: [
        { provide: NbDialogRef, useValue: nbDialogRefStub },
        { provide: NbDialogService, useValue: nbDialogServiceStub },
        { provide: ContactPersonService, useValue: contactPersonServiceSpy },
        { provide: InterviewService, useValue: interviewServiceSpy },
        { provide: QuestionService, useValue: questionServiceSpy },
        { provide: FacCritService, useValue: facCritServiceSpy },
        { provide: AuditService, useValue: auditServiceSpy },
      ],
    });

    fixture = TestBed.createComponent(EditAuditDialogComponent);
    component = fixture.componentInstance;
    // fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
