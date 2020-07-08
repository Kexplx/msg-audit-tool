import { Component, OnInit } from '@angular/core';
import { Subject, Observable, combineLatest } from 'rxjs';
import { debounceTime, filter, map } from 'rxjs/operators';
import { Store, Select } from '@ngxs/store';
import { AppRouterState } from 'src/app/core/ngxs/app-router.state';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';
import { Interview } from 'src/app/core/data/models/interview.model';
import { AuditState } from 'src/app/core/ngxs/audit.state';
import { InterviewState } from 'src/app/core/ngxs/interview.state';
import { AuditService } from 'src/app/core/http/audit.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Answer } from 'src/app/core/data/models/answer.model';
import { UpdateInterview } from 'src/app/core/ngxs/actions/inteview.actions';

@Component({
  selector: 'app-interview-director',
  templateUrl: './interview-director.component.html',
  styleUrls: ['./interview-director.component.scss'],
})
export class InterviewDirectorComponent implements OnInit {
  @Select(AppRouterState.interviewId) interviewId$: Observable<number>;
  @Select(AppRouterState.facCritId) facCritId$: Observable<number>;
  @Select(InterviewState.answers) answers$: Observable<Answer[]>;

  facCrit$: Observable<FacCrit>;
  interview$: Observable<Interview>;

  goal$ = new Subject<string>();

  facCritId: number;
  interviewId: number;
  facCritIds: number[];

  constructor(
    private store: Store,
    private activatedRoute: ActivatedRoute,
    private auditService: AuditService,
    private router: Router,
  ) {}

  ngOnInit() {
    combineLatest(this.interviewId$, this.facCritId$)
      .pipe(filter(ids => ids[0] != null && ids[1] != null))
      .subscribe(ids => {
        this.interviewId = ids[0];
        this.facCritId = ids[1];

        this.interview$ = this.store.select(InterviewState.interview(ids[0]));
        this.facCrit$ = this.store.select(AuditState.facCrit(ids[1]));

        this.auditService.getFacCritsByInterviewId(ids[0]).subscribe((facCrits: FacCrit[]) => {
          this.facCritIds = this.facCritIds = facCrits.map(f => f.id);
        });
      });

    // Dispatch UpdateInterview after 1000ms of last input event
    this.goal$.pipe(debounceTime(1000)).subscribe(goal => {
      this.store.dispatch(new UpdateInterview(this.interviewId, { goal }));
    });
  }

  onGoalInput(value: string) {
    this.goal$.next(value);
  }

  onNavigateForward(facCritId: number) {
    const { id, interviewId } = this.activatedRoute.snapshot.params;
    const indexOfFacCrit = this.facCritIds.indexOf(facCritId);

    const url = `audits/${id}/interviews/${interviewId}/${this.facCritIds[indexOfFacCrit + 1]}`;

    if (indexOfFacCrit + 1 !== this.facCritIds.length) {
      this.router.navigate([url]);
    }
  }

  onNaviagteBack(facCritId: number) {
    const { id, interviewId } = this.activatedRoute.snapshot.params;
    const indexOfFacCrit = this.facCritIds.indexOf(facCritId);

    const url = `audits/${id}/interviews/${interviewId}/${this.facCritIds[indexOfFacCrit - 1]}`;
    if (indexOfFacCrit > 0) {
      this.router.navigate([url]);
    }
  }

  getFacCritPosition(id: number) {
    const indexOfFacCrit = this.facCritIds.indexOf(id) + 1;
    return indexOfFacCrit + '/' + this.facCritIds.length;
  }
}
