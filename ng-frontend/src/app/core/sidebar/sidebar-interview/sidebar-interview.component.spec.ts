import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarInterviewComponent } from './sidebar-interview.component';
import { CoreModule } from 'src/app/core/core.module';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../../../shared/shared.module';
import { ContactPersonService } from '../../http/contact-person.service';
import {
  contactPersonServiceSpy,
  interviewServiceSpy,
  questionServiceSpy,
  facCritServiceSpy,
  auditServiceSpy,
} from '../../ngxs/test/service-spies';
import { InterviewService } from '../../http/interview.service';
import { QuestionService } from '../../http/question.service';
import { FacCritService } from '../../http/facCrit.service';
import { AuditService } from '../../http/audit.service';

describe('SidebarInterviewComponent', () => {
  let component: SidebarInterviewComponent;
  let fixture: ComponentFixture<SidebarInterviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SidebarInterviewComponent],
      imports: [CoreModule, RouterModule.forRoot([]), SharedModule],
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
    fixture = TestBed.createComponent(SidebarInterviewComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
