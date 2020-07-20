import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditCardComponent } from './audit-card.component';
import { RouterModule } from '@angular/router';
import { SharedModule } from 'src/app/shared/shared.module';
import { CoreModule } from 'src/app/core/core.module';
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

describe('AuditCardComponent', () => {
  let component: AuditCardComponent;
  let fixture: ComponentFixture<AuditCardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AuditCardComponent],
      imports: [RouterModule.forRoot([]), SharedModule, CoreModule],
      providers: [
        { provide: ContactPersonService, useValue: contactPersonServiceSpy },
        { provide: InterviewService, useValue: interviewServiceSpy },
        { provide: QuestionService, useValue: questionServiceSpy },
        { provide: FacCritService, useValue: facCritServiceSpy },
        { provide: AuditService, useValue: auditServiceSpy },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(AuditCardComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
