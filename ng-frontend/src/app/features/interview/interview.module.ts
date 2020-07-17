import { NgModule } from '@angular/core';

import { InterviewRoutingModule } from './interview-routing.module';
import { InterviewComponent } from './interview.component';
import { AnswerQuestionListComponent } from './answer-question-list/answer-question-list.component';
import { ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from 'src/app/shared/shared.module';
import { QuestionByIdPipe } from './answer-question-list/questions-by-id.pipe';
import { AnswersByIdsPipe } from './answers-by-ids.pipe';

@NgModule({
  declarations: [
    InterviewComponent,
    AnswerQuestionListComponent,
    QuestionByIdPipe,
    AnswersByIdsPipe,
  ],
  imports: [ReactiveFormsModule, SharedModule, InterviewRoutingModule],
})
export class InterviewModule {}
