import { Component, ViewChild, TemplateRef } from '@angular/core';
import { NbDialogRef, NbDialogService } from '@nebular/theme';
import { defaultDialogOptions } from '../../../shared/components/dialogs/default-dialog-options';
import { Location } from '@angular/common';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';
import { ContactPersonStore } from 'src/app/core/data/stores/contact-person.store';

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
    private contactPersonStore: ContactPersonStore,
    private location: Location,
  ) {}

  ngAfterViewInit() {
    this.dialogRef = this.dialogService.open(this.dialog, defaultDialogOptions);
    this.dialogRef.onClose.subscribe(() => {
      this.location.back();
    });
  }

  onSubmit(contactPerson: ContactPerson) {
    this.contactPersonStore.addContactPerson(contactPerson);
    this.dialogRef.close();
  }

  onCancel() {
    this.dialogRef.close();
  }
}
