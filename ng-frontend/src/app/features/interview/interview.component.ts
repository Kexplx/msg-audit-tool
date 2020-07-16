import { Component, OnInit } from '@angular/core';
import { Select, Store } from '@ngxs/store';
import { AppRouterState } from 'src/app/core/ngxs/app-router.state';
import { Observable, Subject, combineLatest } from 'rxjs';
import { InterviewState } from 'src/app/core/ngxs/interview.state';
import { Answer } from 'src/app/core/data/models/answer.model';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';
import { Interview, InterviewStatus } from 'src/app/core/data/models/interview.model';
import { ActivatedRoute, Router } from '@angular/router';
import { FacCritService } from 'src/app/core/http/facCrit.service';
import { filter, debounceTime } from 'rxjs/operators';
import { AuditState } from 'src/app/core/ngxs/audit.state';
import { UpdateInterview } from 'src/app/core/ngxs/actions/inteview.actions';
import { Audit } from 'src/app/core/data/models/audit.model';

@Component({
  selector: 'app-interview',
  templateUrl: './interview.component.html',
  styleUrls: ['./interview.component.scss'],
})
export class InterviewComponent implements OnInit {
  @Select(AppRouterState.interviewId) interviewId$: Observable<number>;
  @Select(AppRouterState.facCritId) facCritId$: Observable<number>;
  @Select(AppRouterState.auditId) auditId$: Observable<number>;
  @Select(InterviewState.answers) answers$: Observable<Answer[]>;
  note$ = new Subject<string>();

  facCrit: FacCrit;
  interview: Interview;
  audit: Audit;

  facCritId: number;
  interviewId: number;
  facCritIds: number[];

  constructor(
    private store: Store,
    private activatedRoute: ActivatedRoute,
    private facCritService: FacCritService,
    private router: Router,
  ) {}

  ngOnInit() {
    combineLatest(this.interviewId$, this.facCritId$, this.auditId$)
      .pipe(filter(ids => ids[0] != null && ids[1] != null))
      .subscribe(ids => {
        this.interviewId = ids[0];
        this.facCritId = ids[1];

        const interview$ = this.store.select(InterviewState.interview(ids[0]));
        const facCrit$ = this.store.select(AuditState.facCrit(ids[1]));
        const audit$ = this.store.select(AuditState.audit(ids[2]));

        combineLatest(interview$, facCrit$, audit$).subscribe(entities => {
          this.interview = entities[0];
          this.facCrit = entities[1];
          this.audit = entities[2];
        });

        this.facCritService.getFacCritsByInterviewId(ids[0]).subscribe((facCrits: FacCrit[]) => {
          this.facCritIds = this.facCritIds = facCrits.map(f => f.id);
        });
      });

    // Dispatch UpdateInterview after 1000ms of last input event
    this.note$.pipe(debounceTime(1000)).subscribe(note => {
      this.store.dispatch(new UpdateInterview(this.interviewId, { note }));
    });
  }

  onNoteInput(value: string) {
    this.note$.next(value);
  }

  onNavigateForward(facCritId: number) {
    const { auditId, interviewId } = this.activatedRoute.snapshot.params;
    const indexOfFacCrit = this.facCritIds.indexOf(facCritId);

    const url = `audits/${auditId}/interviews/${interviewId}/${
      this.facCritIds[indexOfFacCrit + 1]
    }`;

    if (indexOfFacCrit + 1 !== this.facCritIds.length) {
      this.router.navigate([url]);
    }
  }

  onNaviagteBack(facCritId: number) {
    const { auditId, interviewId } = this.activatedRoute.snapshot.params;
    const indexOfFacCrit = this.facCritIds.indexOf(facCritId);

    const url = `audits/${auditId}/interviews/${interviewId}/${
      this.facCritIds[indexOfFacCrit - 1]
    }`;
    if (indexOfFacCrit > 0) {
      this.router.navigate([url]);
    }
  }

  getFacCritPosition(id: number) {
    const indexOfFacCrit = this.facCritIds.indexOf(id) + 1;
    return indexOfFacCrit + '/' + this.facCritIds.length;
  }

  onFinishClick() {
    this.store.dispatch(
      new UpdateInterview(this.interviewId, { status: InterviewStatus.Finished }),
    );
  }
}
