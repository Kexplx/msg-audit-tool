import { Pipe, PipeTransform } from '@angular/core';
import { Interview } from 'src/app/core/data/models/interview.model';

@Pipe({
  name: 'sortInterview',
})
export class SortInterviewPipe implements PipeTransform {
  transform(interviews: Interview[], facritId: string) {
    return interviews?.filter(x => x.facCrit.id === facritId);
  }
}