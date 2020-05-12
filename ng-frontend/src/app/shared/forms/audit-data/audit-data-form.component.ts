import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Audit, AuditStatus } from 'src/app/data/models/audit.model';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { factors } from 'src/app/data/categories';
import { NbDialogService } from '@nebular/theme';
import { Factor } from 'src/app/data/models/factor.model';
import { ConfirmDiscardDialogComponent } from '../../dialogs/confirm-discard-dialog/confirm-discard-dialog.component';

@Component({
  selector: 'app-audit-data-form',
  templateUrl: './audit-data-form.component.html',
  styleUrls: ['./audit-data-form.component.scss'],
})
export class AuditDataFormComponent implements OnInit {
  @Input() audit: Audit;
  @Input() submitButtonName: string;
  @Input() cancelButtonName: string;

  @Output() formSubmitted = new EventEmitter<Audit>();
  @Output() cancelled = new EventEmitter<any>();

  formFactors: Factor[];
  auditForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private dialogService: NbDialogService) {}

  //#region Getters
  get auditName() {
    return this.auditForm.get('auditName');
  }

  get start() {
    return this.auditForm.get('start');
  }

  get end() {
    return this.auditForm.get('end');
  }

  get companyName() {
    return this.auditForm.get('companyName');
  }

  get sector() {
    return this.auditForm.get('sector');
  }

  get department() {
    return this.auditForm.get('department');
  }

  get salutation() {
    return this.auditForm.get('salutation');
  }

  get title() {
    return this.auditForm.get('title');
  }

  get firstName() {
    return this.auditForm.get('firstName');
  }

  get lastName() {
    return this.auditForm.get('lastName');
  }

  get contactInformation() {
    return this.auditForm.get('contactInformation');
  }

  get status() {
    return this.auditForm.get('status');
  }
  //#endregion

  ngOnInit(): void {
    if (this.audit?.factors) {
      // Get all category titles in audit
      const categoryTitles = [];
      for (const factor of this.audit.factors) {
        for (const category of factor.categories) {
          categoryTitles.push(category.title);
        }
      }

      // Set selected property of category to true if contained in categoryTitles
      this.formFactors = factors.map(factor => {
        const formCategories = factor.categories.map(x => {
          return { title: x.title, selected: categoryTitles.includes(x.title) };
        });

        const hasSelectedCategory = formCategories.find(x => x.selected) != undefined;
        return { categories: formCategories, title: factor.title, selected: hasSelectedCategory };
      });
    } else {
      // Select every factor and category
      this.formFactors = factors.map(x => ({ ...x, selected: true }));
      this.formFactors.forEach(x => {
        x.categories = x.categories.map(x => ({ ...x, selected: true }));
      });
    }

    this.auditForm = this.formBuilder.group({
      auditName: [this.audit?.name, Validators.required],
      start: [this.audit?.start],
      end: [this.audit?.end],
      status: [this.audit?.status ?? AuditStatus.IsPlanned],
      companyName: [this.audit?.customerData.name],
      sector: [this.audit?.customerData.sector],
      department: [this.audit?.customerData.department],
      salutation: [this.audit?.contactPerson.salutation],
      title: [this.audit?.contactPerson.title],
      firstName: [this.audit?.contactPerson.firstName],
      lastName: [this.audit?.contactPerson.lastName],
      contactInformation: [this.audit?.contactPerson.information],
    });
  }

  onSubmit() {
    const filteredFactors = this.filterFactors(this.formFactors);

    const audit: Audit = {
      name: this.auditName.value,
      status: +this.status.value,
      contactPerson: {
        salutation: this.salutation.value,
        title: this.title.value,
        firstName: this.firstName.value,
        lastName: this.lastName.value,
        information: this.contactInformation.value,
      },
      customerData: {
        department: this.department.value,
        name: this.companyName.value,
        sector: this.sector.value,
      },
      start: this.parseDate(this.start.value),
      end: this.parseDate(this.end.value),
      factors: [...filteredFactors],
    };

    this.formSubmitted.emit(audit);
  }

  /**
   * Removes factors that don't contain a selected category
   * and removes "selected" property from factors and categories
   *
   * @param factors The factors to filter
   */
  filterFactors(factors: Factor[]) {
    const filteredFactors = factors.filter(x => x.categories.findIndex(x => x['selected']) != -1);

    filteredFactors.forEach(x => {
      x.categories = x.categories.filter(x => x['selected']);
      delete x['selected'];
      x.categories.forEach(x => delete x['selected']);
    });

    return filteredFactors;
  }

  onCancel() {
    if (this.auditForm.dirty) {
      const confirmDiscardComponentRef = this.dialogService.open(ConfirmDiscardDialogComponent, {
        autoFocus: false,
        closeOnBackdropClick: false,
      });

      confirmDiscardComponentRef.componentRef.instance.onDiscardConfirm.subscribe(
        (cancelConfirmed: boolean) => {
          if (cancelConfirmed) {
            this.cancelled.emit();
          }
        },
      );
    } else {
      this.cancelled.emit();
    }
  }

  onFactorSelect(factor: Factor) {
    factor['selected'] = !factor['selected'];
    factor.categories.forEach(x => (x['selected'] = factor['selected']));
  }

  parseDate(s: string) {
    return s ? new Date(s).getTime() : undefined;
  }
}
