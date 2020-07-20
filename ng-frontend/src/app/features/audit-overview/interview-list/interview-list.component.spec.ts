import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InterviewListComponent } from './interview-list.component';
import { CoreModule } from 'src/app/core/core.module';
import { Router } from '@angular/router';
import { ContactPersonService } from 'src/app/core/data/http/contact-person.service';
import { InterviewService } from 'src/app/core/data/http/interview.service';
import { QuestionService } from 'src/app/core/data/http/question.service';
import { FacCritService } from 'src/app/core/data/http/facCrit.service';
import { AuditService } from 'src/app/core/data/http/audit.service';
import { contactPersonServiceSpy } from 'src/app/core/data/http/test/spies/contact-person.service.spy';
import { interviewServiceSpy } from 'src/app/core/data/http/test/spies/interview.service.spy';
import { facCritServiceSpy } from 'src/app/core/data/http/test/spies/faccrit.service.spy';
import { auditServiceSpy } from 'src/app/core/data/http/test/spies/audit.service.spy';

describe('InterviewListComponent', () => {
  let component: InterviewListComponent;
  let fixture: ComponentFixture<InterviewListComponent>;

  beforeEach(async(() => {
    const routerStub = { url: '/audits/123/interviews' };

    TestBed.configureTestingModule({
      declarations: [InterviewListComponent],
      imports: [CoreModule],
      providers: [
        { provide: Router, useValue: routerStub },
        { provide: ContactPersonService, useValue: contactPersonServiceSpy },
        { provide: InterviewService, useValue: interviewServiceSpy },
        { provide: FacCritService, useValue: facCritServiceSpy },
        { provide: AuditService, useValue: auditServiceSpy },
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InterviewListComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
