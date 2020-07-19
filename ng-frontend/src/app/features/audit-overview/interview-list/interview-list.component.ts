import { Component, OnInit, OnDestroy } from '@angular/core';
import { Audit } from 'src/app/core/data/models/audit.model';
import { Observable } from 'rxjs';
import { Select } from '@ngxs/store';
import { AppRouterState } from 'src/app/core/ngxs/app-router.state';
import { Interview, InterviewStatus } from 'src/app/core/data/models/interview.model';
import { map } from 'rxjs/operators';
import { InterviewStore } from 'src/app/core/stores/interview.store';
import { SubSink } from 'subsink';
import { AuditStore } from 'src/app/core/stores/audit.store';
import { IdService } from 'src/app/core/id.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-interview-list',
  templateUrl: './interview-list.component.html',
  styleUrls: ['./interview-list.component.scss'],
})
export class InterviewListComponent implements OnInit, OnDestroy {
  audit: Audit;
  interviews: Interview[];

  private readonly subSink = new SubSink();

  constructor(
    private auditStore: AuditStore,
    private routesService: IdService,
    private interviewStore: InterviewStore,
  ) {}

  ngOnInit() {
    const idSub = this.routesService.auditId$.subscribe(id => {
      const auditSub = this.auditStore.audits$
        .pipe(map(audits => audits?.find(a => a.id === id)))
        .subscribe(audit => (this.audit = audit));

      const interviewSub = this.interviewStore.interviews$
        .pipe(map(interviews => interviews?.filter(i => i.auditId === id)))
        .subscribe(interviews => {
          this.interviews = interviews;
        });

      this.subSink.add(auditSub, interviewSub);
      this.interviewStore.loadInterviewsByAuditId(id);
    });

    this.subSink.add(idSub);
    this.auditStore.loadAudits();
  }

  ngOnDestroy() {
    this.subSink.unsubscribe();
  }

  /**
   * Get's all interviews that contain answers which have
   * faccrit id's of either the factor or it's criteria
   *
   * @param id The id of the factor.
   */
  interviewsByFactorId(id: number): Interview[] {
    const facCritIds = [
      id,
      ...this.audit.scope.filter(fc => fc.referenceId === id).map(fc => fc.id),
    ];

    const result = this.interviews.filter(i =>
      i.answers.find(a => facCritIds.includes(a.faccritId)),
    );

    return result;
  }

  /**
   * Checks if an array of interviews contains unfinished interviews
   *
   * @param interviews The interviews to check.
   */
  hasUnfinishedInterviews(interviews: Interview[]): boolean {
    return (
      interviews.find(i => i.status !== InterviewStatus.Finished) && interviews.length > 0 !== null
    );
  }
}
