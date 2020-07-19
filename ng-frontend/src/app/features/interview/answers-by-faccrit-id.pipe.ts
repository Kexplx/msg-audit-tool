import { Pipe, PipeTransform } from '@angular/core';
import { Answer } from 'src/app/core/data/models/answer.model';

@Pipe({
  name: 'answersByFacCritId',
})
export class AnswersByFacCritId implements PipeTransform {
  transform(answers: Answer[], facCritId: number): Answer[] {
    return answers.filter(a => a.faccritId === facCritId);
  }
}
