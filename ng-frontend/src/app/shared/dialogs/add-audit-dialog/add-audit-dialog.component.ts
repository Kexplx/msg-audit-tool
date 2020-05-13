import { Component, OnInit, ViewChild, TemplateRef, AfterViewInit } from '@angular/core';
import { NbDialogRef, NbDialogService } from '@nebular/theme';
import { Router } from '@angular/router';
import { Store } from '@ngxs/store';
import { Audit } from 'src/app/data/models/audit.model';
import { AddAudit } from 'src/app/ngxs/audit.actions';
import { defaultDialogOptions } from '../default-dialog-options';

@Component({
  selector: 'app-add-audit-dialog',
  templateUrl: './add-audit-dialog.component.html',
  styleUrls: ['./add-audit-dialog.component.scss'],
})
export class AddAuditDialogComponent implements AfterViewInit {
  @ViewChild('dialog') dialog: TemplateRef<any>;
  dialogRef: NbDialogRef<any>;

  constructor(
    private dialogService: NbDialogService,
    private store: Store,
    private router: Router,
  ) {}

  ngAfterViewInit() {
    this.dialogRef = this.dialogService.open(this.dialog, defaultDialogOptions);
    this.dialogRef.onClose.subscribe(() => {
      this.router.navigate(['/audits']);
    });
  }

  onSubmit(audit: Audit) {
    this.store.dispatch(new AddAudit(audit)).subscribe(() => this.dialogRef.close());
  }

  onCancel() {
    this.dialogRef.close();
  }
}
