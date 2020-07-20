import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Validators, FormBuilder, FormControl } from '@angular/forms';
import { Audit, AuditStatus } from 'src/app/core/data/models/audit.model';
import { NbDialogService } from '@nebular/theme';
import { dateRangeValidator } from 'src/app/shared/components/forms/form-helpers';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';
import { Observable, of } from 'rxjs';
import { AbstractFormComponent } from '../../../shared/components/forms/abstract-form-component';
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
  @Input() contactPersons: ContactPerson[];
  @Input() facCrits: FacCrit[];

  @Output() formSubmitted = new EventEmitter<Partial<Audit>>();

  filteredContactPersons$: Observable<ContactPerson[]>;
  selectedContactPersons: SelectedContactPerson[] = [];

  constructor(private formBuilder: FormBuilder, protected dialogService: NbDialogService) {
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

    this.filteredContactPersons$ = of(this.contactPersons);
    this.selectedContactPersons =
      this.audit?.contactPersons.map(cp => ({
        company: cp.companyName,
        name: cp.forename + ' ' + cp.surname,
        value: cp,
      })) ?? [];

    for (const facCrit of this.facCrits) {
      const inAudit = this.audit
        ? this.audit.scope.findIndex(x => x.id === facCrit.id) != -1
        : defaultScopeComplement.includes(facCrit.id) ||
          defaultScopeComplement.includes(facCrit.referenceId)
        ? false
        : true;

      this.formGroup.addControl(String(facCrit.id), new FormControl(inAudit));
    }
  }

  onSubmit() {
    const audit: Partial<Audit> = {
      id: this.audit?.id,
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
    for (const crit of this.facCrits) {
      const checked = this.formGroup.get(String(crit.id)).value;

      if (checked) {
        result.push(crit);
      }
    }

    return result;
  }

  /**
   * Checks or unchecks all criterias of a factor.
   */
  toggleCriterias(factorId: string, checked: boolean) {
    for (const facCrit of this.facCrits.filter(x => x.referenceId === +factorId)) {
      this.formGroup.get(String(facCrit.id)).setValue(checked);
    }
  }

  filterContactPersonOptions(input: string) {
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
