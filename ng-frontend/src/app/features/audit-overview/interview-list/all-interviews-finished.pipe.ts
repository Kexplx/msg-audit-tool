import { Pipe, PipeTransform } from '@angular/core';
import { Interview, InterviewStatus } from 'src/app/core/data/models/interview.model';

@Pipe({
  name: 'allInterviewsFinished',
})
export class AllInterviewsFinishedPipe implements PipeTransform {
  /**
   * Checks if all interviews are finished and returns true if they are.
   *
   * @param interviews The interviews to check.
   */
  transform(interviews: Interview[]): boolean {
    return interviews?.filter(i => i.status === InterviewStatus.Active).length === 0;
  }
}
