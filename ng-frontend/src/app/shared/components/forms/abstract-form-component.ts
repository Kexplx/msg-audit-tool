import { NbDialogService } from '@nebular/theme';
import { FormGroup } from '@angular/forms';
import { ConfirmDiscardDialogComponent } from '../dialogs/confirm-discard-dialog/confirm-discard-dialog.component';
import { Output, EventEmitter, Input } from '@angular/core';

export abstract class AbstractFormComponent {
  @Input() submitButtonName: string;
  @Input() cancelButtonName: string;
  @Output() cancelled = new EventEmitter<any>();

  formGroup: FormGroup;

  constructor(protected dialogService: NbDialogService) {}

  onCancel() {
    if (this.formGroup.dirty && this.formGroup.touched) {
      const confirmDiscardComponentRef = this.dialogService.open(ConfirmDiscardDialogComponent, {
        autoFocus: false,
        closeOnBackdropClick: false,
      });

      confirmDiscardComponentRef.componentRef.instance.onDiscardConfirm.subscribe(
        (cancelConfirmed: boolean) => {
          if (cancelConfirmed) {
            this.cancelled.emit();
          }
        },
      );
    } else {
      this.cancelled.emit();
    }
  }
}
