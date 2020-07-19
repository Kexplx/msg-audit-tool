import { Component, ViewChild, TemplateRef, AfterViewInit, OnInit } from '@angular/core';
import { NbDialogService, NbDialogRef } from '@nebular/theme';
import { Select } from '@ngxs/store';
import { Location } from '@angular/common';
import { defaultDialogOptions } from 'src/app/shared/components/dialogs/default-dialog-options';
import { Observable } from 'rxjs';
import { Audit } from 'src/app/core/data/models/audit.model';
import { Interview } from 'src/app/core/data/models/interview.model';
import { AppRouterState } from 'src/app/core/ngxs/app-router.state';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';
import { filter, map } from 'rxjs/operators';
import { InterviewStore } from 'src/app/core/stores/interview.store';
import { AuditStore } from 'src/app/core/stores/audit.store';

@Component({
  selector: 'app-new-interview-dialog',
  templateUrl: './add-interview-dialog.component.html',
  styleUrls: ['./add-interview-dialog.component.scss'],
})
export class AddInterviewDialogComponent implements AfterViewInit, OnInit {
  @Select(AppRouterState.auditId) auditId$: Observable<number>;
  @ViewChild('dialog') dialog: TemplateRef<any>;
  dialogRef: NbDialogRef<any>;

  audit$: Observable<Audit>;
  auditId: number;

  constructor(
    private dialogService: NbDialogService,
    private interviewStore: InterviewStore,
    private auditService: AuditStore,
    private location: Location,
  ) {}

  ngOnInit() {
    this.auditId$.subscribe(id => {
      this.auditId = id;

      this.audit$ = this.auditService.audits$.pipe(
        filter(audits => audits != null),
        map(audits => audits.find(a => a.id === id)),
      );
    });
  }

  ngAfterViewInit() {
    this.dialogRef = this.dialogService.open(this.dialog, defaultDialogOptions);

    this.dialogRef.onClose.subscribe(() => {
      this.location.back();
    });
  }

  /**
   * Dispatches AddInterview action to add the submitted interview
   *
   * @param interview The interview filled out in the form
   */
  onSubmit(interview: Interview, scope: FacCrit[]) {
    this.interviewStore.addInterview({ ...interview, auditId: this.auditId }, scope);
    this.dialogRef.close();
  }

  onCancel() {
    this.dialogRef.close();
  }
}
