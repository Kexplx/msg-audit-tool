import { Pipe, PipeTransform } from '@angular/core';
import { Question } from 'src/app/core/data/models/question.model';

@Pipe({
  name: 'questionById',
})
export class QuestionByIdPipe implements PipeTransform {
  transform(questions: Question[], id: number): Question {
    return questions.find(q => q.id === id);
  }
}
