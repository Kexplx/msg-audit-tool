import { Component, ViewChild, TemplateRef, AfterViewInit } from '@angular/core';
import { NbDialogRef, NbDialogService } from '@nebular/theme';
import { Store, Select } from '@ngxs/store';
import { defaultDialogOptions } from '../default-dialog-options';
import { Audit } from 'src/app/core/data/models/audit.model';
import { AddAudit } from 'src/app/core/ngxs/actions/audit.actions';
import { Location } from '@angular/common';
import { AuditRegistryState } from 'src/app/core/ngxs/audit-registry.state';
import { Observable } from 'rxjs';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';
import { ContactPersonState } from 'src/app/core/ngxs/contact-people.state';

@Component({
  selector: 'app-add-audit-dialog',
  templateUrl: './add-audit-dialog.component.html',
  styleUrls: ['./add-audit-dialog.component.scss'],
})
export class AddAuditDialogComponent implements AfterViewInit {
  @ViewChild('dialog') dialog: TemplateRef<any>;
  @Select(ContactPersonState.contactPeople) contactPeople$: Observable<ContactPerson[]>;
  dialogRef: NbDialogRef<any>;

  constructor(
    private dialogService: NbDialogService,
    private store: Store,
    private location: Location,
  ) {}

  ngAfterViewInit() {
    this.dialogRef = this.dialogService.open(this.dialog, {
      ...defaultDialogOptions,
      autoFocus: true,
    });
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
