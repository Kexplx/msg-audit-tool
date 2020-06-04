import { Component, OnInit, ViewChild, TemplateRef } from '@angular/core';
import { NbDialogRef, NbDialogService } from '@nebular/theme';
import { Store } from '@ngxs/store';
import { Router } from '@angular/router';
import { defaultDialogOptions } from '../default-dialog-options';
import { Audit } from 'src/app/core/data/models/audit.model';
import { AddAudit } from 'src/app/core/ngxs/audit.actions';
import { Location } from '@angular/common';

@Component({
  selector: 'app-add-contact-person-dialog',
  templateUrl: './add-contact-person-dialog.component.html',
  styleUrls: ['./add-contact-person-dialog.component.scss'],
})
export class AddContactPersonDialogComponent {
  @ViewChild('dialog') dialog: TemplateRef<any>;
  dialogRef: NbDialogRef<any>;

  constructor(
    private dialogService: NbDialogService,
    private store: Store,
    private router: Router,
    private location: Location,
  ) {}

  ngAfterViewInit() {
    this.dialogRef = this.dialogService.open(this.dialog, defaultDialogOptions);
    this.dialogRef.onClose.subscribe(() => {
      this.location.back();
    });
  }

  onSubmit(audit: Audit) {
    this.store.dispatch(new AddAudit(audit)).subscribe(() => this.dialogRef.close());
  }

  onCancel() {
    this.dialogRef.close();
  }
}
