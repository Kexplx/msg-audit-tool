import { Component, ViewChild, TemplateRef } from '@angular/core';
import { NbDialogRef, NbDialogService } from '@nebular/theme';
import { Store } from '@ngxs/store';
import { defaultDialogOptions } from '../default-dialog-options';
import { AddContactPerson } from 'src/app/core/ngxs/audit.actions';
import { Location } from '@angular/common';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';

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
    private location: Location,
  ) {}

  ngAfterViewInit() {
    this.dialogRef = this.dialogService.open(this.dialog, defaultDialogOptions);
    this.dialogRef.onClose.subscribe(() => {
      this.location.back();
    });
  }

  onSubmit(contactPerson: ContactPerson) {
    this.store
      .dispatch(new AddContactPerson(contactPerson))
      .subscribe(() => this.dialogRef.close());
  }

  onCancel() {
    this.dialogRef.close();
  }
}
