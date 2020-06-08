import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Interview, InterviewStatus } from 'src/app/core/data/models/interview.model';
import { FormBuilder, Validators } from '@angular/forms';
import { NbDialogService } from '@nebular/theme';
import { Select } from '@ngxs/store';
import { AuditRegistryState } from 'src/app/core/ngxs/audit-registry.state';
import { Observable } from 'rxjs';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';
import { AbstractFormComponent } from '../abstract-form-component';

@Component({
  selector: 'app-interview-form',
  templateUrl: './interview-form.component.html',
  styleUrls: ['./interview-form.component.scss'],
})
export class InterviewFormComponent extends AbstractFormComponent implements OnInit {
  @Input() interview: Interview;
  @Input() facCrits: FacCrit[];
  @Output() formSubmitted = new EventEmitter<Interview>();

  @Select(AuditRegistryState.contactPeople) contactPeople$: Observable<ContactPerson[]>;

  constructor(private fb: FormBuilder, protected dialogService: NbDialogService) {
    super(dialogService);
  }

  get startDate() {
    return this.formGroup.get('startDate');
  }

  get facCrit() {
    return this.formGroup.get('facCrit');
  }

  get contactPeople() {
    return this.formGroup.get('contactPeople');
  }

  ngOnInit() {
    this.formGroup = this.fb.group({
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
}
