import { Pipe, PipeTransform } from '@angular/core';
import { Interview, InterviewStatus } from 'src/app/core/data/models/interview.model';

@Pipe({
  name: 'finishedInterviewsCount',
})
export class FinishedInterviewsCountPipe implements PipeTransform {
  /**
   * Checks if all interviews are finished and returns true if they are.
   *
   * @param interviews The interviews to check.
   */
  transform(interviews: Interview[]): number {
    if (!interviews || interviews?.length === 0) return 0;
    return interviews.filter(i => i.status === InterviewStatus.Finished).length;
  }
}
