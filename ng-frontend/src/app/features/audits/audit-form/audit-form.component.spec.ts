import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditFormComponent } from './audit-form.component';
import { FormBuilder } from '@angular/forms';
import { NbDialogService } from '@nebular/theme';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SharedModule } from 'src/app/shared/shared.module';
import { CoreModule } from 'src/app/core/core.module';
import { RouterModule } from '@angular/router';
import { ContactPersonService } from 'src/app/core/data/http/contact-person.service';
import {
  contactPersonServiceSpy,
  interviewServiceSpy,
  questionServiceSpy,
  facCritServiceSpy,
  auditServiceSpy,
} from 'src/app/core/ngxs/test/service-spies';
import { InterviewService } from 'src/app/core/data/http/interview.service';
import { QuestionService } from 'src/app/core/data/http/question.service';
import { FacCritService } from 'src/app/core/data/http/facCrit.service';
import { AuditService } from 'src/app/core/data/http/audit.service';

describe('AuditFormComponent', () => {
  let component: AuditFormComponent;
  let fixture: ComponentFixture<AuditFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AuditFormComponent],
      imports: [SharedModule, BrowserAnimationsModule, CoreModule, RouterModule.forRoot([])],
      providers: [
        NbDialogService,
        FormBuilder,
        { provide: ContactPersonService, useValue: contactPersonServiceSpy },
        { provide: InterviewService, useValue: interviewServiceSpy },
        { provide: QuestionService, useValue: questionServiceSpy },
        { provide: FacCritService, useValue: facCritServiceSpy },
        { provide: AuditService, useValue: auditServiceSpy },
      ],
    });

    fixture = TestBed.createComponent(AuditFormComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
