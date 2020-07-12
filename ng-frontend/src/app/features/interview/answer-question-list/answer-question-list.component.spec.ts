import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnswerQuestionListComponent } from './answer-question-list.component';
import { CoreModule } from 'src/app/core/core.module';
import { RouterModule } from '@angular/router';

describe('AnswerQuestionListComponent', () => {
  let component: AnswerQuestionListComponent;
  let fixture: ComponentFixture<AnswerQuestionListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AnswerQuestionListComponent],
      imports: [CoreModule, RouterModule.forRoot([])],
    }).compileComponents();
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
