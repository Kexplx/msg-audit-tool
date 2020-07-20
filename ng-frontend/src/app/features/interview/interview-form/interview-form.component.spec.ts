/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { InterviewFormComponent } from './interview-form.component';
import { FormBuilder } from '@angular/forms';
import { SharedModule } from 'src/app/shared/shared.module';
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

describe('InterviewFormComponent', () => {
  let component: InterviewFormComponent;
  let fixture: ComponentFixture<InterviewFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [InterviewFormComponent],
      imports: [SharedModule, CoreModule, RouterModule.forRoot([])],
      providers: [
        FormBuilder,
        { provide: ContactPersonService, useValue: contactPersonServiceSpy },
        { provide: InterviewService, useValue: interviewServiceSpy },
        { provide: QuestionService, useValue: questionServiceSpy },
        { provide: FacCritService, useValue: facCritServiceSpy },
        { provide: AuditService, useValue: auditServiceSpy },
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InterviewFormComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
