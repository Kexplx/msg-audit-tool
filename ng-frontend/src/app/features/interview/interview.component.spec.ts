import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InterviewComponent } from './interview.component';
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

describe('InterviewComponent', () => {
  let component: InterviewComponent;
  let fixture: ComponentFixture<InterviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [InterviewComponent],
      imports: [CoreModule, RouterModule.forRoot([])],
      providers: [
        { provide: ContactPersonService, useValue: contactPersonServiceSpy },
        { provide: InterviewService, useValue: interviewServiceSpy },
        { provide: QuestionService, useValue: questionServiceSpy },
        { provide: FacCritService, useValue: facCritServiceSpy },
        { provide: AuditService, useValue: auditServiceSpy },
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InterviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
