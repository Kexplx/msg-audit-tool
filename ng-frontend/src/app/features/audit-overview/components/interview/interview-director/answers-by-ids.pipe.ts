import { Pipe, PipeTransform } from '@angular/core';
import { Answer } from 'src/app/core/data/models/answer.model';

@Pipe({
  name: 'answerByIds',
})
export class AnswersByIdsPipe implements PipeTransform {
  transform(answers: Answer[], interviewId: number, facCritId: number): Answer[] {
    return answers?.filter(a => a.faccritId === facCritId && a.interviewId === interviewId);
  }
}
