import { Component, ViewChild, TemplateRef, AfterViewInit, OnInit } from '@angular/core';
import { NbDialogService, NbDialogRef } from '@nebular/theme';
import { Store } from '@ngxs/store';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { defaultDialogOptions } from 'src/app/shared/components/dialogs/default-dialog-options';
import { AuditRegistryState } from 'src/app/core/ngxs/audit-registry.state';
import { Observable } from 'rxjs';
import { Audit } from 'src/app/core/data/models/audit.model';
import { Interview } from 'src/app/core/data/models/interview.model';
import { AddInterview } from 'src/app/core/ngxs/actions/audit.actions';

@Component({
  selector: 'app-new-interview-dialog',
  templateUrl: './new-interview-dialog.component.html',
  styleUrls: ['./new-interview-dialog.component.scss'],
})
export class NewInterviewDialogComponent implements AfterViewInit, OnInit {
  @ViewChild('dialog') dialog: TemplateRef<any>;
  dialogRef: NbDialogRef<any>;

  audit$: Observable<Audit>;

  constructor(
    private dialogService: NbDialogService,
    private store: Store,
    private router: Router,
    private location: Location,
  ) {}

  /**
   * Parses the audit id from router.url and selects the
   * audit as an observable from the store
   */
  ngOnInit() {
    const idRegex = /\/audits\/([^\/]*)\/.*/gm;
    const id = idRegex.exec(this.router.url)[1];

    this.audit$ = this.store.select(AuditRegistryState.audit(id));
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
  onSubmit(interview: Interview) {
    let audit: Audit;
    this.audit$.subscribe(x => (audit = x));

    this.store.dispatch(new AddInterview(audit, interview)).subscribe(() => {
      this.dialogRef.close();
    });
  }

  onCancel() {
    this.dialogRef.close();
  }
}
