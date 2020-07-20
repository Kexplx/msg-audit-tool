/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarComponent } from './sidebar.component';
import { RouterModule } from '@angular/router';
import { CoreModule } from '../../core.module';
import { ContactPersonService } from '../../data/http/contact-person.service';
import { InterviewService } from '../../data/http/interview.service';
import { FacCritService } from '../../data/http/facCrit.service';
import { AuditService } from '../../data/http/audit.service';
import { contactPersonServiceSpy } from '../../data/http/test/spies/contact-person.service.spy';
import { interviewServiceSpy } from '../../data/http/test/spies/interview.service.spy';
import { facCritServiceSpy } from '../../data/http/test/spies/faccrit.service.spy';
import { auditServiceSpy } from '../../data/http/test/spies/audit.service.spy';

describe('SidebarComponent', () => {
  let component: SidebarComponent;
  let fixture: ComponentFixture<SidebarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SidebarComponent],
      imports: [RouterModule.forRoot([]), CoreModule],
      providers: [
        { provide: ContactPersonService, useValue: contactPersonServiceSpy },
        { provide: InterviewService, useValue: interviewServiceSpy },
        { provide: FacCritService, useValue: facCritServiceSpy },
        { provide: AuditService, useValue: auditServiceSpy },
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidebarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
