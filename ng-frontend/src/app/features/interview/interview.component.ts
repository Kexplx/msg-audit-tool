import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject, combineLatest, Observable } from 'rxjs';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';
import { Interview, InterviewStatus } from 'src/app/core/data/models/interview.model';
import { ActivatedRoute, Router } from '@angular/router';
import { filter, debounceTime, map, tap } from 'rxjs/operators';
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
export class InterviewComponent implements OnInit {
  note$ = new Subject<string>();

  interview$: Observable<Interview>;
  audit$: Observable<Audit>;
  facCrit$: Observable<FacCrit>;

  interview: Interview;

  facCritGroupedIds: number[];

  auditId: number;
  interviewId: number;
  facCritId: number;

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
    this.auditId = auditId;
    this.interviewId = interviewId;

    this.activatedRoute.paramMap.subscribe(paramMap => {
      this.facCritId = +paramMap.get('facCritId');
    });

    this.interviewService.interviews$.pipe(
      filter(interviews => interviews != null),
      map(interviews => interviews.find(i => i.id === this.interviewId)),
      tap(interview => {
        this.interview = interview;
        this.getGroupedFacCritIds(interview);
      }),
    );

    this.answerStore.loadAnswersByInterviewId(this.interviewId);
    this.interviewService.loadInterviewsByAuditId(this.auditId);

    // Dispatch UpdateInterview after 1000ms of last input event
    this.note$.pipe(debounceTime(1000)).subscribe(note => {
      this.interviewService.updateInterview({ ...this.interview, note });
    });
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

  private getGroupedFacCritIds(interview: Interview) {
    const facCritIds = interview.answers.map(a => a.faccritId);
    this.facCritGroupedIds = this.facCritService.getGroupedFacCrits(facCritIds).map(fc => fc.id);
  }
}
