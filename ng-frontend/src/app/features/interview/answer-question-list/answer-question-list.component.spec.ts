import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnswerQuestionListComponent } from './answer-question-list.component';
import { CoreModule } from 'src/app/core/core.module';
import { RouterModule } from '@angular/router';
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

describe('AnswerQuestionListComponent', () => {
  let component: AnswerQuestionListComponent;
  let fixture: ComponentFixture<AnswerQuestionListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AnswerQuestionListComponent],
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
    fixture = TestBed.createComponent(AnswerQuestionListComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
