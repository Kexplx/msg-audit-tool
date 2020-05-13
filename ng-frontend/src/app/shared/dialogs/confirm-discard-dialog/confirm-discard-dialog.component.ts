import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { NbDialogRef } from '@nebular/theme';

@Component({
  selector: 'app-confirm-discard-dialog',
  templateUrl: './confirm-discard-dialog.component.html',
  styleUrls: ['./confirm-discard-dialog.component.scss'],
})
export class ConfirmDiscardDialogComponent implements OnInit {
  @Output() onDiscardConfirm = new EventEmitter<boolean>();
  constructor(private dialogRef: NbDialogRef<any>) {}

  ngOnInit(): void {}

  onOk() {
    this.onDiscardConfirm.emit(true);
    this.closeDialog();
  }

  onCancel() {
    this.onDiscardConfirm.emit(false);
    this.closeDialog();
  }

  closeDialog() {
    this.dialogRef.close();
  }
}
