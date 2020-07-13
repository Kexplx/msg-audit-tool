import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Validators, FormBuilder, FormControl } from '@angular/forms';
import { Audit, AuditStatus } from 'src/app/core/data/models/audit.model';
import { NbDialogService } from '@nebular/theme';
import { dateRangeValidator } from 'src/app/shared/components/forms/form-helpers';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';
import { Select, Store } from '@ngxs/store';
import { AuditState } from 'src/app/core/ngxs/audit.state';
import { Observable, of } from 'rxjs';
import { AbstractFormComponent } from '../abstract-form-component';
import { ContactPersonState } from 'src/app/core/ngxs/contact-person.state';
import { map } from 'rxjs/operators';

const defaultScopeComplement = [8, 14];

interface SelectedContactPerson {
  name: string;
  company: string;
  value: ContactPerson;
}

@Component({
  selector: 'app-audit-form',
  templateUrl: './audit-form.component.html',
  styleUrls: ['./audit-form.component.scss'],
})
export class AuditFormComponent extends AbstractFormComponent implements OnInit {
  @Input() audit: Audit;
  @Output() formSubmitted = new EventEmitter<Partial<Audit>>();

  @Select(ContactPersonState.contactPersons) contactPersons$: Observable<ContactPerson[]>;
  @Select(AuditState.facCrits) facCrits$: Observable<FacCrit[]>;

  contactPersons: ContactPerson[];
  filteredContactPersons$: Observable<ContactPerson[]>;

  selectedContactPersons: SelectedContactPerson[] = [];

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

  ngOnInit() {
    this.contactPersons = this.store.selectSnapshot(ContactPersonState.contactPersons);
    this.filteredContactPersons$ = of(this.contactPersons);
    this.selectedContactPersons =
      this.audit?.contactPersons.map(cp => ({
        company: cp.companyName,
        name: cp.forename + ' ' + cp.surname,
        value: cp,
      })) ?? [];

    const defaultStartDate = new Date();
    defaultStartDate.setHours(0, 0, 0, 0);

    this.formGroup = this.formBuilder.group(
      {
        name: [this.audit?.name, Validators.required],
        startDate: [this.audit?.startDate ?? defaultStartDate, Validators.required],
        endDate: [this.audit?.endDate],
      },
      { validator: dateRangeValidator('startDate', 'endDate') },
    );

    this.facCrits$.subscribe(facCrits => {
      for (const facCrit of facCrits) {
        const inAudit = this.audit
          ? this.audit.scope.findIndex(x => x.id === facCrit.id) != -1
          : defaultScopeComplement.includes(facCrit.id) ||
            defaultScopeComplement.includes(facCrit.referenceId)
          ? false
          : true;

        this.formGroup.addControl(String(facCrit.id), new FormControl(inAudit));
      }
    });
  }

  onSubmit() {
    const audit: Partial<Audit> = {
      name: this.name.value,
      endDate: this.endDate.value,
      startDate: this.startDate.value,
      status: this.audit?.status ?? AuditStatus.Planned,
      contactPersons: this.selectedContactPersons.map(cp => cp.value),
      scope: this.checkedFacCrits(),
    };

    this.formSubmitted.emit(audit);
  }

  checkedFacCrits() {
    const result: FacCrit[] = [];

    this.facCrits$.subscribe(facCrits => {
      for (const crit of facCrits) {
        const checked = this.formGroup.get(String(crit.id)).value;

        if (checked) {
          result.push(crit);
        }
      }
    });

    return result;
  }

  toggleCriteriaChecked(factorId: string, checked: true) {
    this.store.select(AuditState.facCritByReferenceId(+factorId)).subscribe(x => {
      for (const crit of x) {
        this.formGroup.get(String(crit.id)).setValue(checked);
      }
    });
  }

  filterOptions(input: string) {
    this.filteredContactPersons$ = of(this.contactPersons).pipe(
      map(options => options.filter(o => o.forename.toLowerCase().startsWith(input.toLowerCase()))),
    );
  }

  onContactPersonSelected(cp: ContactPerson) {
    if (this.selectedContactPersons.map(x => x.value).find(y => y.id === cp.id)) return;

    this.selectedContactPersons.push({
      company: cp.companyName,
      name: cp.forename + ' ' + cp.surname,
      value: cp,
    });
  }

  onRemoveContactPerson(cp: SelectedContactPerson) {
    const indexOf = this.selectedContactPersons.indexOf(cp);
    this.selectedContactPersons.splice(indexOf, 1);
  }
}
