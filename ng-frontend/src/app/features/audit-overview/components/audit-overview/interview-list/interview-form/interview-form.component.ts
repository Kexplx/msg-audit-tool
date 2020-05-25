import { Component, OnInit, Input } from '@angular/core';
import { Interview } from 'src/app/core/data/models/interview.model';
import { FormGroup, FormBuilder, FormControl, FormArray, AbstractControl } from '@angular/forms';

@Component({
  selector: 'app-interview-form',
  templateUrl: './interview-form.component.html',
  styleUrls: ['./interview-form.component.scss'],
})
export class InterviewFormComponent implements OnInit {
  @Input() interview: Interview;
  interviewForm: FormGroup;

  constructor(private fb: FormBuilder) {}

  get persons(): FormArray {
    return this.interviewForm.get('persons') as FormArray;
  }

  get start() {
    return this.interviewForm.get('start');
  }

  get end() {
    return this.interviewForm.get('end');
  }

  ngOnInit() {
    this.interviewForm = this.fb.group({
      start: [this.interview?.start ?? new Date()],
      end: [this.interview?.end, this.startGreaterThanEndValidator.bind(this)],
      persons: this.fb.array([
        this.fb.group({
          role: new FormControl(null),
          contactInformation: new FormControl(null),
        }),
        this.fb.group({
          role: new FormControl(null),
          contactInformation: new FormControl(null),
        }),
      ]),
    });

    console.log(this.persons);
  }

  onAddPerson() {
    this.persons.push(
      this.fb.group({
        role: new FormControl(null),
        contactInformation: new FormControl(null),
      }),
    );
  }

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
    const interview: Interview = {
      start: this.parseDate(this.start.value),
      end: this.parseDate(this.end.value),
      persons: this.persons.controls.map((x: FormGroup) => {
        return { information: x.controls.contactInformation.value, role: x.controls.role.value };
      }),
      criteria: this.criteria.value,
      factorTitle: this.factorTitle.value,
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
