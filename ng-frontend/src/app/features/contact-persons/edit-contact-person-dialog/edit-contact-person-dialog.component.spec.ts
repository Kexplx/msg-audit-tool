/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditContactPersonDialogComponent } from './edit-contact-person-dialog.component';
import { RouterModule } from '@angular/router';
import { SharedModule } from 'src/app/shared/shared.module';
import { CoreModule } from 'src/app/core/core.module';
import { CommonModule } from '@angular/common';
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

describe('EditContactPersonDialogComponent', () => {
  let component: EditContactPersonDialogComponent;
  let fixture: ComponentFixture<EditContactPersonDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [EditContactPersonDialogComponent],
      imports: [RouterModule.forRoot([]), SharedModule, CoreModule, CommonModule],
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
    fixture = TestBed.createComponent(EditContactPersonDialogComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
