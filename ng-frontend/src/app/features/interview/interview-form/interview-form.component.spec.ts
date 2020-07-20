/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { InterviewFormComponent } from './interview-form.component';
import { FormBuilder } from '@angular/forms';
import { SharedModule } from 'src/app/shared/shared.module';
import { CoreModule } from 'src/app/core/core.module';
import { RouterModule } from '@angular/router';
import { ContactPersonService } from 'src/app/core/data/http/contact-person.service';
import { InterviewService } from 'src/app/core/data/http/interview.service';
import { QuestionService } from 'src/app/core/data/http/question.service';
import { FacCritService } from 'src/app/core/data/http/facCrit.service';
import { AuditService } from 'src/app/core/data/http/audit.service';
import { contactPersonServiceSpy } from 'src/app/core/data/http/test/spies/contact-person.service.spy';
import { interviewServiceSpy } from 'src/app/core/data/http/test/spies/interview.service.spy';
import { facCritServiceSpy } from 'src/app/core/data/http/test/spies/faccrit.service.spy';
import { auditServiceSpy } from 'src/app/core/data/http/test/spies/audit.service.spy';

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
