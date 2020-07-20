import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject, combineLatest } from 'rxjs';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';
import { Interview, InterviewStatus } from 'src/app/core/data/models/interview.model';
import { ActivatedRoute, Router } from '@angular/router';
import { filter, debounceTime, map } from 'rxjs/operators';
import { Audit } from 'src/app/core/data/models/audit.model';
import { AuditStore } from 'src/app/core/stores/audit.store';
import { InterviewStore } from 'src/app/core/stores/interview.store';
import { FacCritStore } from 'src/app/core/stores/faccrit.store';
import { SubSink } from 'subsink';
import { AnswerStore } from 'src/app/core/stores/answer.store';

@Component({
  selector: 'app-interview',
  templateUrl: './interview.component.html',
  styleUrls: ['./interview.component.scss'],
})
export class InterviewComponent implements OnInit, OnDestroy {
  note$ = new Subject<string>();

  facCrit: FacCrit;
  interview: Interview;
  audit: Audit;
  facCritGroupedIds: number[];

  private auditId: number;
  private interviewId: number;
  private facCritId: number;

  private readonly subSink = new SubSink();

  constructor(
    private activatedRoute: ActivatedRoute,
    private auditService: AuditStore,
    private interviewService: InterviewStore,
    private facCritService: FacCritStore,
    private answerStore: AnswerStore,
    private router: Router,
  ) {}

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(paramMap => {
      this.auditId = +paramMap.get('auditId');
      this.facCritId = +paramMap.get('facCritId');
      this.interviewId = +paramMap.get('interviewId');

      const audit$ = this.auditService.audits$.pipe(
        filter(audits => audits != null),
        map(audits => audits.find(i => i.id === this.auditId)),
      );

      const interview$ = this.interviewService.interviews$.pipe(
        filter(interviews => interviews != null),
        map(interviews => interviews.find(i => i.id === this.interviewId)),
      );

      const facCrit$ = this.facCritService.facCrits$.pipe(
        filter(facCrits => facCrits != null),
        map(facCrits => facCrits.find(a => a.id === this.facCritId)),
      );

      this.subSink.add(
        combineLatest(audit$, interview$, facCrit$).subscribe(observables => {
          this.audit = observables[0];
          this.interview = observables[1];
          this.facCrit = observables[2];

          this.answerStore.loadAnswersByInterviewId(observables[1].id);
          this.getGroupedFacCritIds();
        }),
      );
    });
    this.interviewService.loadInterviews();

    // Dispatch UpdateInterview after 1000ms of last input event
    this.note$.pipe(debounceTime(1000)).subscribe(note => {
      this.interviewService.updateInterview({ ...this.interview, note });
    });
  }

  ngOnDestroy() {
    this.subSink.unsubscribe();
  }

  getGroupedFacCritIds() {
    const facCritIds = this.interview.answers.map(a => a.faccritId);
    this.facCritGroupedIds = this.facCritService.groupedFacCrits(facCritIds).map(fc => fc.id);
  }

  onNoteInput(value: string) {
    this.note$.next(value);
  }

  onNavigateForward(facCritId: number) {
    const indexOfFacCrit = this.facCritGroupedIds.indexOf(facCritId);
    const url = `audits/${this.auditId}/interviews/${this.interviewId}/${
      this.facCritGroupedIds[indexOfFacCrit + 1]
    }`;

    if (indexOfFacCrit + 1 !== this.facCritGroupedIds.length) {
      this.router.navigate([url]);
    }
  }

  onNaviagteBack(facCritId: number) {
    const indexOfFacCrit = this.facCritGroupedIds.indexOf(facCritId);

    const url = `audits/${this.auditId}/interviews/${this.interviewId}/${
      this.facCritGroupedIds[indexOfFacCrit - 1]
    }`;
    if (indexOfFacCrit > 0) {
      this.router.navigate([url]);
    }
  }

  getFacCritPosition(id: number) {
    const indexOfFacCrit = this.facCritGroupedIds.indexOf(id) + 1;
    return indexOfFacCrit + '/' + this.facCritGroupedIds.length;
  }

  onFinishClick() {
    this.interviewService.updateInterview({ ...this.interview, status: InterviewStatus.Finished });
  }
}
