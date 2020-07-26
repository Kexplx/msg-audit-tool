import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject, Observable } from 'rxjs';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';
import { Interview, InterviewStatus } from 'src/app/core/data/models/interview.model';
import { ActivatedRoute, Router } from '@angular/router';
import { filter, debounceTime, map } from 'rxjs/operators';
import { Audit } from 'src/app/core/data/models/audit.model';
import { AuditStore } from 'src/app/core/data/stores/audit.store';
import { InterviewStore } from 'src/app/core/data/stores/interview.store';
import { FacCritStore } from 'src/app/core/data/stores/faccrit.store';
import { SubSink } from 'subsink';
import { AnswerStore } from 'src/app/core/data/stores/answer.store';

@Component({
  selector: 'app-interview',
  templateUrl: './interview.component.html',
  styleUrls: ['./interview.component.scss'],
})
export class InterviewComponent implements OnInit, OnDestroy {
  private readonly subSink = new SubSink();

  note$ = new Subject<string>();
  audits$: Observable<Audit[]>;
  facCrits$: Observable<FacCrit[]>;

  facCritGroupedIds: number[];
  interview: Interview;
  interviewId: number;
  facCritId: number;
  auditId: number;

  constructor(
    private activatedRoute: ActivatedRoute,
    private auditService: AuditStore,
    private interviewService: InterviewStore,
    private facCritService: FacCritStore,
    private answerStore: AnswerStore,
    private router: Router,
  ) {}

  ngOnInit() {
    const { auditId, interviewId } = this.activatedRoute.snapshot.params;
    this.auditId = +auditId;
    this.interviewId = +interviewId;

    this.subSink.add(
      this.activatedRoute.paramMap.subscribe(paramMap => {
        this.facCritId = +paramMap.get('facCritId');
      }),
    );

    this.audits$ = this.auditService.audits$;
    this.facCrits$ = this.facCritService.facCrits$;

    this.subSink.add(
      this.interviewService.interviews$
        .pipe(
          filter(interviews => interviews != null),
          map(interviews => interviews.find(i => i.id === this.interviewId)),
        )
        .subscribe(interview => {
          this.interview = interview;
          this.setGroupedFacCritIds(interview);
        }),
    );

    this.interviewService.loadInterviewsByAuditId(this.auditId);
    this.answerStore.loadAnswersByInterviewId(this.interviewId);

    // Dispatch UpdateInterview after 1000ms of last input event
    this.subSink.add(
      this.note$.pipe(debounceTime(1000)).subscribe(note => {
        this.interviewService.updateInterview({ ...this.interview, note });
      }),
    );
  }

  ngOnDestroy(): void {
    this.subSink.unsubscribe();
  }

  onNoteInput(value: string) {
    this.note$.next(value);
  }

  onNavigateForward(facCritId: number): void {
    const indexOfFacCrit = this.facCritGroupedIds.indexOf(facCritId);

    const url = `audits/${this.auditId}/interviews/${this.interviewId}/${
      this.facCritGroupedIds[indexOfFacCrit + 1]
    }`;

    if (indexOfFacCrit + 1 !== this.facCritGroupedIds.length) {
      this.router.navigate([url]);
    }
  }

  onNaviagteBack(facCritId: number): void {
    const indexOfFacCrit = this.facCritGroupedIds.indexOf(facCritId);

    const url = `audits/${this.auditId}/interviews/${this.interviewId}/${
      this.facCritGroupedIds[indexOfFacCrit - 1]
    }`;
    if (indexOfFacCrit > 0) {
      this.router.navigate([url]);
    }
  }

  getFacCritPosition(id: number): string {
    const indexOfFacCrit = this.facCritGroupedIds.indexOf(id) + 1;
    return indexOfFacCrit + '/' + this.facCritGroupedIds.length;
  }

  onFinishClick(): void {
    this.interviewService.updateInterview({ ...this.interview, status: InterviewStatus.Finished });
  }

  private setGroupedFacCritIds(interview: Interview): void {
    const facCritIds = interview.answers.map(a => a.faccritId);

    this.facCritService.facCrits$.pipe(filter(facCrits => facCrits != null)).subscribe(() => {
      this.facCritGroupedIds = this.facCritService.getGroupedFacCrits(facCritIds).map(fc => fc.id);
    });
  }
}
