import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Interview, InterviewStatus } from 'src/app/core/data/models/interview.model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NbDialogService } from '@nebular/theme';
import { ConfirmDiscardDialogComponent } from 'src/app/shared/components/dialogs/confirm-discard-dialog/confirm-discard-dialog.component';
import { Select } from '@ngxs/store';
import { AuditRegistryState } from 'src/app/core/ngxs/audit-registry.state';
import { Observable } from 'rxjs';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';

@Component({
  selector: 'app-interview-form',
  templateUrl: './interview-form.component.html',
  styleUrls: ['./interview-form.component.scss'],
})
export class InterviewFormComponent implements OnInit {
  @Input() interview: Interview;
  @Input() facCrits: FacCrit[];
  @Output() formSubmitted = new EventEmitter<Interview>();
  @Output() formCancelled = new EventEmitter<any>();
  interviewForm: FormGroup;

  @Select(AuditRegistryState.contactPeople) contactPeople$: Observable<ContactPerson[]>;

  constructor(private fb: FormBuilder, private dialogService: NbDialogService) {}

  get startDate() {
    return this.interviewForm.get('startDate');
  }

  get facCrit() {
    return this.interviewForm.get('facCrit');
  }

  get contactPeople() {
    return this.interviewForm.get('contactPeople');
  }

  ngOnInit() {
    this.interviewForm = this.fb.group({
      startDate: [this.interview?.start ?? new Date()],
      facCrit: [this.interview?.facCrit, Validators.required],
      contactPeople: [this.interview?.contactPeople, Validators.required],
    });
  }

  onSubmit() {
    const interview: Interview = {
      start: this.startDate.value,
      status: InterviewStatus.InAction,
      facCrit: this.facCrit.value,
      contactPeople: this.contactPeople.value,
    };

    this.formSubmitted.emit(interview);
  }

  onCancel() {
    if (this.interviewForm.dirty && this.interviewForm.touched) {
      const confirmDiscardComponentRef = this.dialogService.open(ConfirmDiscardDialogComponent, {
        autoFocus: false,
        closeOnBackdropClick: false,
      });

      confirmDiscardComponentRef.componentRef.instance.onDiscardConfirm.subscribe(
        (cancelConfirmed: boolean) => {
          if (cancelConfirmed) {
            this.formCancelled.emit();
          }
        },
      );
    } else {
      this.formCancelled.emit();
    }
  }
}
