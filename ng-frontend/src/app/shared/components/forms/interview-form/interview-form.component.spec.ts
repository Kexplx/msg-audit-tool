/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { InterviewFormComponent } from './interview-form.component';
import { FormBuilder } from '@angular/forms';
import { SharedModule } from 'src/app/shared/shared.module';

describe('InterviewFormComponent', () => {
  let component: InterviewFormComponent;
  let fixture: ComponentFixture<InterviewFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [InterviewFormComponent],
      imports: [SharedModule],
      providers: [FormBuilder],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InterviewFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // it('should create', () => {
  //   expect(component).toBeTruthy();
  // });
});
