import { Component, ViewChild, TemplateRef, AfterViewInit, OnInit } from '@angular/core';
import { NbDialogService, NbDialogRef } from '@nebular/theme';
import { Store, Select } from '@ngxs/store';
import { Location } from '@angular/common';
import { defaultDialogOptions } from 'src/app/shared/components/dialogs/default-dialog-options';
import { AuditState } from 'src/app/core/ngxs/audit.state';
import { Observable } from 'rxjs';
import { Audit } from 'src/app/core/data/models/audit.model';
import { Interview } from 'src/app/core/data/models/interview.model';
import { AppRouterState } from 'src/app/core/ngxs/app-router.state';
import { AddInterview } from 'src/app/core/ngxs/actions/inteview.actions';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';

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
    private store: Store,
    private location: Location,
  ) {}

  ngOnInit() {
    this.auditId$.subscribe(id => {
      this.auditId = id;
      this.audit$ = this.store.select(AuditState.audit(id));
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
    this.store.dispatch(new AddInterview({ ...interview, auditId: this.auditId }, scope));
    this.dialogRef.close();
  }

  onCancel() {
    this.dialogRef.close();
  }
}
