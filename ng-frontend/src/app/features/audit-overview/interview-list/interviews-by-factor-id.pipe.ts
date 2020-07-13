import { Pipe, PipeTransform } from '@angular/core';
import { Interview } from 'src/app/core/data/models/interview.model';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';

@Pipe({
  name: 'interviewsInCriteria',
})
export class InterviewsByFactorIdPipe implements PipeTransform {
  transform(interviews: Interview[], criterias: FacCrit[]): boolean {
    return (
      interviews?.filter(
        interview =>
          interview.answers.findIndex(answer => criterias.find(c => c.id === answer.faccritId)) !=
          -1,
      ).length > 0
    );
  }
}
