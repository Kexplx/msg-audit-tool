import { Component, OnInit, ViewChild, TemplateRef, AfterViewInit } from '@angular/core';
import { NbDialogRef, NbDialogService } from '@nebular/theme';
import { Router } from '@angular/router';
import { Store } from '@ngxs/store';
import { defaultDialogOptions } from '../default-dialog-options';
import { Audit } from 'src/app/core/data/models/audit.model';
import { AddAudit } from 'src/app/core/ngxs/audit.actions';

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
    this.dialogRef = this.dialogService.open(this.dialog, {
      ...defaultDialogOptions,
      autoFocus: true,
    });
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
