import { Component, OnInit, OnDestroy } from '@angular/core';
import { Select } from '@ngxs/store';
import { AppRouterState } from 'src/app/core/ngxs/app-router.state';
import { Observable, Subject, combineLatest } from 'rxjs';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';
import { Interview, InterviewStatus } from 'src/app/core/data/models/interview.model';
import { ActivatedRoute, Router } from '@angular/router';
import { filter, debounceTime, map } from 'rxjs/operators';
import { Audit } from 'src/app/core/data/models/audit.model';
import { AuditStore } from 'src/app/core/stores/audit.store';
import { InterviewStore } from 'src/app/core/stores/interview.store';
import { FacCritStore } from 'src/app/core/stores/faccrit.store';
import { SubSink } from 'subsink';

@Component({
  selector: 'app-interview',
  templateUrl: './interview.component.html',
  styleUrls: ['./interview.component.scss'],
})
export class InterviewComponent implements OnInit, OnDestroy {
  @Select(AppRouterState.interviewId) interviewId$: Observable<number>;
  @Select(AppRouterState.facCritId) facCritId$: Observable<number>;
  @Select(AppRouterState.auditId) auditId$: Observable<number>;
  note$ = new Subject<string>();

  private readonly subSink = new SubSink();

  facCrit: FacCrit;
  interview: Interview;
  audit: Audit;

  facCritGroupedIds: number[];

  constructor(
    private activatedRoute: ActivatedRoute,
    private auditService: AuditStore,
    private interviewService: InterviewStore,
    private facCritService: FacCritStore,
    private router: Router,
  ) {}

  ngOnInit() {
    const sub = combineLatest(this.auditId$, this.interviewId$, this.facCritId$)
      .pipe(filter(ids => ids[0] != null && ids[1] != null && ids[2] != null))
      .subscribe(ids => {
        const auditSub = this.auditService.audits$.pipe(
          filter(audits => audits != null),
          map(audits => audits.find(i => i.id === ids[0])),
        );

        const interviewSub = this.interviewService.interviews$.pipe(
          filter(interviews => interviews != null),
          map(interviews => interviews.find(i => i.id === ids[1])),
        );

        const facCritSub = this.facCritService.facCrits$.pipe(
          filter(facCrits => facCrits != null),
          map(facCrits => facCrits.find(a => a.id === ids[2])),
        );

        combineLatest(auditSub, interviewSub, facCritSub).subscribe(observables => {
          this.audit = observables[0];
          this.interview = observables[1];
          this.facCrit = observables[2];

          this.groupedFacCritIds();
        });
      });

    this.subSink.add(sub);

    this.auditService.loadAudits();
    this.interviewService.loadInterviews();
    this.facCritService.loadFacCrits();

    // Dispatch UpdateInterview after 1000ms of last input event
    this.note$.pipe(debounceTime(1000)).subscribe(note => {
      this.interviewService.updateInterview({ ...this.interview, note });
    });
  }

  ngOnDestroy() {
    this.subSink.unsubscribe();
  }

  groupedFacCritIds() {
    const facCritIds = this.interview.answers.map(a => a.faccritId);
    this.facCritGroupedIds = this.facCritService.groupedFacCrits(facCritIds).map(fc => fc.id);
  }

  onNoteInput(value: string) {
    this.note$.next(value);
  }

  onNavigateForward(facCritId: number) {
    const { auditId, interviewId } = this.activatedRoute.snapshot.params;
    const indexOfFacCrit = this.facCritGroupedIds.indexOf(facCritId);

    const url = `audits/${auditId}/interviews/${interviewId}/${
      this.facCritGroupedIds[indexOfFacCrit + 1]
    }`;

    if (indexOfFacCrit + 1 !== this.facCritGroupedIds.length) {
      this.router.navigate([url]);
    }
  }

  onNaviagteBack(facCritId: number) {
    const { auditId, interviewId } = this.activatedRoute.snapshot.params;
    const indexOfFacCrit = this.facCritGroupedIds.indexOf(facCritId);

    const url = `audits/${auditId}/interviews/${interviewId}/${
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
