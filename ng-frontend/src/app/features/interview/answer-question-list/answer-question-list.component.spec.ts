import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnswerQuestionListComponent } from './answer-question-list.component';

describe('AnswerQuestionListComponent', () => {
  let component: AnswerQuestionListComponent;
  let fixture: ComponentFixture<AnswerQuestionListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnswerQuestionListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnswerQuestionListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
