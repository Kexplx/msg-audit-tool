import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarInterviewListComponent } from './sidebar-interview-list.component';
import { CoreModule } from 'src/app/core/core.module';
import { FactorsPipe } from '../../../shared/pipes/factors.pipe';
import { CriteriaByFactorIdPipe } from '../../../features/audit-overview/interview-list/criteria-by-factor-id.pipe';
import { SharedModule } from '../../../shared/shared.module';
import { RouterModule } from '@angular/router';
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

describe('SidebarInterviewListComponent', () => {
  let component: SidebarInterviewListComponent;
  let fixture: ComponentFixture<SidebarInterviewListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SidebarInterviewListComponent],
      imports: [CoreModule, SharedModule, RouterModule.forRoot([])],
      providers: [
        FactorsPipe,
        CriteriaByFactorIdPipe,
        { provide: ContactPersonService, useValue: contactPersonServiceSpy },
        { provide: InterviewService, useValue: interviewServiceSpy },
        { provide: QuestionService, useValue: questionServiceSpy },
        { provide: FacCritService, useValue: facCritServiceSpy },
        { provide: AuditService, useValue: auditServiceSpy },
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidebarInterviewListComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
