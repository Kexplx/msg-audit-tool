/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { InterviewCardComponent } from './interview-card.component';

describe('InterviewCardComponent', () => {
  const routerStub = { url: '/audits/123/interviews' };
  let component: InterviewCardComponent;
  let fixture: ComponentFixture<InterviewCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [InterviewCardComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InterviewCardComponent);
    component = fixture.componentInstance;
    component.interview = { criteria: { title: '123' } };
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
