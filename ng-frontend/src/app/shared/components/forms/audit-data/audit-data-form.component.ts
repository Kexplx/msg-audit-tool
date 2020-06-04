import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, Validators, FormBuilder, AbstractControl } from '@angular/forms';
import { ConfirmDiscardDialogComponent } from '../../dialogs/confirm-discard-dialog/confirm-discard-dialog.component';
import { Audit, AuditStatus } from 'src/app/core/data/models/audit.model';
import { NbDialogService } from '@nebular/theme';
import { dateRangeValidator } from 'src/app/shared/helpers/form-helpers';

@Component({
  selector: 'app-audit-data-form',
  templateUrl: './audit-data-form.component.html',
  styleUrls: ['./audit-data-form.component.scss'],
})
export class AuditDataFormComponent implements OnInit {
  @Input() audit: Audit;
  @Input() submitButtonName: string;
  @Input() cancelButtonName: string;

  @Output() formSubmitted = new EventEmitter<Partial<Audit>>();
  @Output() cancelled = new EventEmitter<any>();

  auditForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private dialogService: NbDialogService) {}

  //#region Getters
  get name() {
    return this.auditForm.get('name');
  }

  get startDate() {
    return this.auditForm.get('startDate');
  }

  get endDate() {
    return this.auditForm.get('endDate');
  }

  get creationDate() {
    return this.auditForm.get('creationDate');
  }

  ngOnInit(): void {
    this.auditForm = this.formBuilder.group(
      {
        name: [this.audit?.name, Validators.required],
        startDate: [this.audit?.startDate ?? new Date(), Validators.required],
        endDate: [this.audit?.endDate],
      },
      { validator: dateRangeValidator('startDate', 'endDate') },
    );
  }

  onCancel() {
    if (this.auditForm.dirty && this.auditForm.touched) {
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

  onSubmit() {
    const audit: Partial<Audit> = {
      name: this.name.value,
      creationDate: this.audit?.creationDate ?? Date.now(),
      endDate: this.endDate.value,
      startDate: this.startDate.value,
      status: this.audit?.status ?? AuditStatus.Planned,
    };

    this.formSubmitted.emit(audit);
  }
}
