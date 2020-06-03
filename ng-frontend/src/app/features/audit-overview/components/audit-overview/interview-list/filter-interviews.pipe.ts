import { Pipe, PipeTransform } from '@angular/core';
import { Interview } from 'src/app/core/data/models/interview.model';

@Pipe({
  name: 'filterInterviews',
})
export class FilterInterviewsPipe implements PipeTransform {
  transform(interviews: Interview[], factorTitle: string): any {
    // return interviews?.filter(x => x. === factorTitle);
  }
}
