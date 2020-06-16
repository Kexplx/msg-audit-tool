import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Interview, InterviewStatus } from 'src/app/core/data/models/interview.model';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { NbDialogService } from '@nebular/theme';
import { Select, Store } from '@ngxs/store';
import { AuditState } from 'src/app/core/ngxs/audit.state';
import { Observable } from 'rxjs';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';
import { AbstractFormComponent } from '../abstract-form-component';
import { ContactPersonState } from 'src/app/core/ngxs/contact-people.state';

@Component({
  selector: 'app-interview-form',
  templateUrl: './interview-form.component.html',
  styleUrls: ['./interview-form.component.scss'],
})
export class InterviewFormComponent extends AbstractFormComponent implements OnInit {
  @Input() interview: Interview;
  @Input() scope: FacCrit[];
  @Output() formSubmitted = new EventEmitter<Interview>();

  @Select(AuditState.facCrits) allFacCrits$: Observable<FacCrit[]>;
  @Select(ContactPersonState.contactPeople) contactPeople$: Observable<ContactPerson[]>;

  facCritSelected = false;

  constructor(
    private fb: FormBuilder,
    protected dialogService: NbDialogService,
    private store: Store,
  ) {
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
      contactPeople: [this.interview?.contactPeople, Validators.required],
    });

    for (const facCrit of this.scope) {
      this.formGroup.addControl(facCrit.id, new FormControl(false));
    }

    this.formGroup.valueChanges.subscribe(x => {
      for (const facCrit of this.scope) {
        if (this.formGroup.get(facCrit.id).value === true) {
          return (this.facCritSelected = true);
        }
      }

      return (this.facCritSelected = false);
    });
  }

  checkedFacCrits() {
    const result: FacCrit[] = [];

    for (const crit of this.scope) {
      const checked = this.formGroup.get(crit.id).value;

      if (checked) {
        result.push(crit);
      }
    }

    return result;
  }

  onSubmit() {
    const interview: Interview = {
      start: this.startDate.value,
      status: InterviewStatus.InAction,
      facCrits: this.checkedFacCrits(),
      contactPeople: this.contactPeople.value,
    };
    this.formSubmitted.emit(interview);
  }

  toggleCriteriaChecked(factorId: string, checked: true) {
    const criteria = this.scope.filter(x => x.referenceId === factorId);

    for (const crit of criteria) {
      this.formGroup.get(crit.id)?.setValue(checked);
    }
  }
}
