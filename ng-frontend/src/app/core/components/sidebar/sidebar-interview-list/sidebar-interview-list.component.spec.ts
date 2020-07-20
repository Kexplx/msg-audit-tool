import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarInterviewListComponent } from './sidebar-interview-list.component';
import { CoreModule } from 'src/app/core/core.module';
import { FactorsPipe } from '../../../../shared/pipes/factors.pipe';
import { CriteriaByFactorIdPipe } from '../../../../features/audit-overview/interview-list/criteria-by-factor-id.pipe';
import { SharedModule } from '../../../../shared/shared.module';
import { RouterModule } from '@angular/router';
import { ContactPersonService } from '../../../data/http/contact-person.service';
import { InterviewService } from '../../../data/http/interview.service';
import { FacCritService } from '../../../data/http/facCrit.service';
import { AuditService } from '../../../data/http/audit.service';
import { contactPersonServiceSpy } from 'src/app/core/data/http/test/spies/contact-person.service.spy';
import { interviewServiceSpy } from 'src/app/core/data/http/test/spies/interview.service.spy';
import { facCritServiceSpy } from 'src/app/core/data/http/test/spies/faccrit.service.spy';
import { auditServiceSpy } from 'src/app/core/data/http/test/spies/audit.service.spy';

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
