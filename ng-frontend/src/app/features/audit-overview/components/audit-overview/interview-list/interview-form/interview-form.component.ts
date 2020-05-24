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
