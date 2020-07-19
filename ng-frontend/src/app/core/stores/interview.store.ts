import { Injectable } from '@angular/core';
import { Interview } from '../data/models/interview.model';
import { FacCrit } from '../data/models/faccrit.model';
import { BehaviorSubject } from 'rxjs';
import { InterviewService } from '../http/interview.service';

@Injectable({
  providedIn: 'root',
})
export class InterviewStore {
  private _interviews$ = new BehaviorSubject<Interview[]>(null);

  get interviews$() {
    return this._interviews$.asObservable();
  }

  constructor(private interviewService: InterviewService) {}

  loadInterviews(): void {
    this.interviewService.getInterviews().subscribe(interviews => {
      this._interviews$.next(interviews);
    });
  }

  loadInterviewsByAuditId(auditId: number): void {
    this.interviewService
      .getInterviewsByAuditId(auditId)
      .subscribe(interviews => this._interviews$.next(interviews));
  }

  addInterview(interview: Interview, interviewScope: FacCrit[]): void {
    this.interviewService.postInterview(interview, interviewScope).subscribe(interview => {
      const interviews = this._interviews$.value;
      this._interviews$.next([...interviews, interview]);
    });
  }

  updateInterview(interview: Interview): void {
    this.interviewService.putInterview(interview).subscribe(interview => {
      const interviews = this._interviews$.value;
      const indexOfUpdatedInterview = interviews.findIndex(i => i.id === interview.id);

      this._interviews$.next([
        ...interviews.slice(0, indexOfUpdatedInterview),
        interview,
        ...interviews.slice(indexOfUpdatedInterview + 1),
      ]);
    });
  }
}
