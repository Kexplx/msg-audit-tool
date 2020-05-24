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
