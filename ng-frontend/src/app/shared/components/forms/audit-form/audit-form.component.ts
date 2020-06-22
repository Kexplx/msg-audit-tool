import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Validators, FormBuilder, FormControl } from '@angular/forms';
import { Audit, AuditStatus } from 'src/app/core/data/models/audit.model';
import { NbDialogService } from '@nebular/theme';
import { dateRangeValidator } from 'src/app/shared/helpers/form-helpers';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';
import { Select, Store } from '@ngxs/store';
import { AuditState } from 'src/app/core/ngxs/audit.state';
import { Observable } from 'rxjs';
import { AbstractFormComponent } from '../abstract-form-component';
import { ContactPersonState } from 'src/app/core/ngxs/contact-people.state';

@Component({
  selector: 'app-audit-form',
  templateUrl: './audit-form.component.html',
  styleUrls: ['./audit-form.component.scss'],
})
export class AuditFormComponent extends AbstractFormComponent implements OnInit {
  @Input() audit: Audit;
  @Output() formSubmitted = new EventEmitter<Partial<Audit>>();

  @Select(ContactPersonState.contactPeople) contactPeople$: Observable<ContactPerson[]>;
  @Select(AuditState.facCrits) facCrits$: Observable<FacCrit[]>;

  constructor(
    private formBuilder: FormBuilder,
    protected dialogService: NbDialogService,
    private store: Store,
  ) {
    super(dialogService);
  }

  get name() {
    return this.formGroup.get('name');
  }

  get startDate() {
    return this.formGroup.get('startDate');
  }

  get endDate() {
    return this.formGroup.get('endDate');
  }

  get creationDate() {
    return this.formGroup.get('creationDate');
  }

  get contactPeopleControl() {
    return this.formGroup.get('contactPeople');
  }

  ngOnInit() {
    const defaultStartDate = new Date();
    defaultStartDate.setHours(0, 0, 0, 0);
    this.formGroup = this.formBuilder.group(
      {
        name: [this.audit?.name, Validators.required],
        startDate: [this.audit?.startDate ?? defaultStartDate, Validators.required],
        endDate: [this.audit?.endDate],
        contactPeople: [this.audit?.contactPeople],
      },
      { validator: dateRangeValidator('startDate', 'endDate') },
    );

    this.facCrits$.subscribe(facCrits => {
      for (const facCrit of facCrits) {
        const inAudit = this.audit
          ? this.audit.scope.findIndex(x => x.id === facCrit.id) != -1
          : true;

        this.formGroup.addControl(facCrit.id, new FormControl(inAudit));
      }
    });
  }

  onSubmit() {
    const audit: Partial<Audit> = {
      name: this.name.value,
      creationDate: this.audit?.creationDate ?? Date.now(),
      endDate: this.endDate.value,
      startDate: this.startDate.value,
      status: this.audit?.status ?? AuditStatus.Planned,
      contactPeople: this.contactPeopleControl.value,
      scope: this.checkedFacCrits(),
    };

    this.formSubmitted.emit(audit);
  }

  checkedFacCrits() {
    const result: FacCrit[] = [];

    this.facCrits$.subscribe(facCrits => {
      for (const crit of facCrits) {
        const checked = this.formGroup.get(crit.id).value;

        if (checked) {
          result.push(crit);
        }
      }
    });

    return result;
  }

  toggleCriteriaChecked(factorId: string, checked: true) {
    this.store.select(AuditState.criteriaByFactorId(factorId)).subscribe(x => {
      for (const crit of x) {
        this.formGroup.get(crit.id).setValue(checked);
      }
    });
  }
}
