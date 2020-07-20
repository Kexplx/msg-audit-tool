import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarInterviewComponent } from './sidebar-interview.component';
import { CoreModule } from 'src/app/core/core.module';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../../../../shared/shared.module';
import { ContactPersonService } from '../../../data/http/contact-person.service';
import { InterviewService } from '../../../data/http/interview.service';
import { QuestionService } from '../../../data/http/question.service';
import { FacCritService } from '../../../data/http/facCrit.service';
import { AuditService } from '../../../data/http/audit.service';
import { contactPersonServiceSpy } from 'src/app/core/data/http/test/spies/contact-person.service.spy';
import { interviewServiceSpy } from 'src/app/core/data/http/test/spies/interview.service.spy';
import { facCritServiceSpy } from 'src/app/core/data/http/test/spies/faccrit.service.spy';
import { auditServiceSpy } from 'src/app/core/data/http/test/spies/audit.service.spy';

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
