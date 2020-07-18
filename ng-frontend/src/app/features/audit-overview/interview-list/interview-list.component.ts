import { Component, OnInit } from '@angular/core';
import { Audit } from 'src/app/core/data/models/audit.model';
import { Observable } from 'rxjs';
import { Select } from '@ngxs/store';
import { AppRouterState } from 'src/app/core/ngxs/app-router.state';
import { Interview, InterviewStatus } from 'src/app/core/data/models/interview.model';
import { map } from 'rxjs/operators';
import { InterviewNewService } from 'src/app/core/http_new/interview-new.service';
import { AudtiNewService } from 'src/app/core/http_new/audit-new.service';

@Component({
  selector: 'app-interview-list',
  templateUrl: './interview-list.component.html',
  styleUrls: ['./interview-list.component.scss'],
})
export class InterviewListComponent implements OnInit {
  @Select(AppRouterState.auditId) auditId$: Observable<number>;

  audit: Audit;
  interviews: Interview[];

  constructor(
    private auditService: AudtiNewService,
    private interviewService: InterviewNewService,
  ) {}

  ngOnInit() {
    this.auditId$.subscribe(id => {
      this.auditService.audits$
        .pipe(map(audits => audits?.find(a => a.id === id)))
        .subscribe(audit => (this.audit = audit));

      this.interviewService.interviews$
        .pipe(map(interviews => interviews?.filter(i => i.auditId === id)))
        .subscribe(interviews => {
          this.interviews = interviews;
        });
    });

    this.interviewService.getInterviews();
    this.auditService.getAudits();
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
