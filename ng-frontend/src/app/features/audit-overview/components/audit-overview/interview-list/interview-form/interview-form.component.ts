import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Interview, InterviewStatus } from 'src/app/core/data/models/interview.model';
import {
  FormGroup,
  FormBuilder,
  FormControl,
  FormArray,
  AbstractControl,
  Validators,
} from '@angular/forms';
import { NbDialogService } from '@nebular/theme';
import { ConfirmDiscardDialogComponent } from 'src/app/shared/components/dialogs/confirm-discard-dialog/confirm-discard-dialog.component';

@Component({
  selector: 'app-interview-form',
  templateUrl: './interview-form.component.html',
  styleUrls: ['./interview-form.component.scss'],
})
export class InterviewFormComponent implements OnInit {
  @Input() interview: Interview;
  // @Input() scope: Factor[];
  @Output() formSubmitted = new EventEmitter<any>();
  @Output() formCancelled = new EventEmitter<any>();
  interviewForm: FormGroup;

  constructor(private fb: FormBuilder, private dialogService: NbDialogService) {}

  get persons(): FormArray {
    return this.interviewForm.get('persons') as FormArray;
  }

  get start() {
    return this.interviewForm.get('start');
  }

  get end() {
    return this.interviewForm.get('end');
  }

  get factorTitle() {
    return this.interviewForm.get('factorTitle');
  }

  get criteria() {
    return this.interviewForm.get('criteria');
  }

  ngOnInit() {
    // this.interviewForm = this.fb.group({
    //   start: [this.interview?.start ?? new Date()],
    //   end: [this.interview?.end, this.startGreaterThanEndValidator.bind(this)],
    //   persons: this.fb.array([
    //     this.fb.group({
    //       role: new FormControl(null),
    //       contactInformation: new FormControl(null),
    //     }),
    //   ]),
    //   criteria: [this.interview?.criteria, Validators.required],
    //   factorTitle: [null],
    // });
  }

  /**
   * Appends a new formgroup to the persons form array
   */
  onAddPerson() {
    this.persons.push(
      this.fb.group({
        role: new FormControl(null),
        contactInformation: new FormControl(null),
      }),
    );
  }

  /**
   * Removes the last formgroup from the persons form array
   */
  onRemovePerson() {
    this.persons.removeAt(this.persons.length - 1);
  }

  parseDate(s: string) {
    return s ? new Date(s).getTime() : undefined;
  }

  startGreaterThanEndValidator(control: AbstractControl): { [s: string]: boolean } {
    const start = this.interview?.start ?? new Date().setHours(0, 0, 0, 0);
    return start > this.parseDate(control.value) ? { startGreaterThanEnd: true } : null;
  }

  onSubmit() {
    // const interview: Interview = {
    //   start: this.parseDate(this.start.value),
    //   end: this.parseDate(this.end.value),
    //   status: InterviewStatus.InAction,
    // };
    // this.formSubmitted.emit(interview);
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
