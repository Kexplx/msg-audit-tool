import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Validators, FormBuilder, FormControl } from '@angular/forms';
import { Audit, AuditStatus } from 'src/app/core/data/models/audit.model';
import { NbDialogService } from '@nebular/theme';
import { dateRangeValidator } from 'src/app/shared/helpers/form-helpers';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';
import { Select } from '@ngxs/store';
import { AuditRegistryState } from 'src/app/core/ngxs/audit-registry.state';
import { Observable } from 'rxjs';
import { AbstractFormComponent } from '../abstract-form-component';

@Component({
  selector: 'app-audit-data-form',
  templateUrl: './audit-data-form.component.html',
  styleUrls: ['./audit-data-form.component.scss'],
})
export class AuditDataFormComponent extends AbstractFormComponent implements OnInit {
  @Input() audit: Audit;
  @Output() formSubmitted = new EventEmitter<Partial<Audit>>();

  @Select(AuditRegistryState.contactPeople) contactPeople$: Observable<ContactPerson[]>;
  @Select(AuditRegistryState.facCrits) facCrits$: Observable<FacCrit[]>;

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

  get creationDate() {
    return this.formGroup.get('creationDate');
  }

  get contactPeopleControl() {
    return this.formGroup.get('contactPeople');
  }

  ngOnInit(): void {
    this.formGroup = this.formBuilder.group(
      {
        name: [this.audit?.name, Validators.required],
        startDate: [this.audit?.startDate ?? new Date().setHours(0, 0, 0, 0), Validators.required],
        endDate: [this.audit?.endDate],
        contactPeople: [this.audit?.contactPeople],
      },
      { validator: dateRangeValidator('startDate', 'endDate') },
    );

    this.facCrits$.subscribe(facCrits => {
      for (const facCrit of facCrits) {
        const inAudit = this.audit
          ? this.audit.facCrits.findIndex(x => x.id === facCrit.id) != -1
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
      facCrits: this.checkedFacCrits(),
    };

    this.formSubmitted.emit(audit);
  }

  checkedFacCrits() {
    const result: FacCrit[] = [];

    this.facCrits$.subscribe(facCrits => {
      for (const facCrit of facCrits) {
        const checked = this.formGroup.get(facCrit.id).value;

        if (checked) {
          const factor = facCrits.find(x => x.id === facCrit.referenceId);
          result.push(facCrit, factor);
        }
      }
    });

    return result;
  }
}
