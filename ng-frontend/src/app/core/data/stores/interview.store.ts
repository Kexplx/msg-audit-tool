import { Injectable } from '@angular/core';
import { Interview } from '../models/interview.model';
import { FacCrit } from '../models/faccrit.model';
import { BehaviorSubject } from 'rxjs';
import { InterviewService } from '../http/interview.service';
import { StoreActionService } from './store-action.service';

@Injectable({
  providedIn: 'root',
})
export class InterviewStore {
  private _interviews$ = new BehaviorSubject<Interview[]>(null);

  get interviews$() {
    return this._interviews$.asObservable();
  }

  constructor(
    private interviewService: InterviewService,
    private storeActionService: StoreActionService,
  ) {}

  loadInterviewsByAuditId(auditId: number): void {
    this.interviewService.getInterviewsByAuditId(auditId).subscribe(interviews => {
      this._interviews$.next(interviews);
      this.storeActionService.notifyLoad('Interviews wurden geladen.');
    });
  }

  addInterview(interview: Interview, interviewScope: FacCrit[]): void {
    this.interviewService.postInterview(interview, interviewScope).subscribe(interview => {
      const interviews = this._interviews$.value;

      if (!interviews) {
        this._interviews$.next([interview]);
      } else {
        this._interviews$.next([...interviews, interview]);
      }

      this.storeActionService.notifyAdd('Interview wurde erstellt.');
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

      this.storeActionService.notifyEdit('Interview wurde aktualisiert.');
    });
  }
}
