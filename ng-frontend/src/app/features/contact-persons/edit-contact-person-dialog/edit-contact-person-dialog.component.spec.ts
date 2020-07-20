/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditContactPersonDialogComponent } from './edit-contact-person-dialog.component';
import { RouterModule } from '@angular/router';
import { SharedModule } from 'src/app/shared/shared.module';
import { CoreModule } from 'src/app/core/core.module';
import { CommonModule } from '@angular/common';
import { ContactPersonService } from 'src/app/core/data/http/contact-person.service';
import { InterviewService } from 'src/app/core/data/http/interview.service';
import { FacCritService } from 'src/app/core/data/http/facCrit.service';
import { AuditService } from 'src/app/core/data/http/audit.service';
import { contactPersonServiceSpy } from 'src/app/core/data/http/test/spies/contact-person.service.spy';
import { interviewServiceSpy } from 'src/app/core/data/http/test/spies/interview.service.spy';
import { facCritServiceSpy } from 'src/app/core/data/http/test/spies/faccrit.service.spy';
import { auditServiceSpy } from 'src/app/core/data/http/test/spies/audit.service.spy';

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
