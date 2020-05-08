import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Audit } from 'src/app/data/models/audit.model';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { factors } from 'src/app/data/categories';
import { NbDialogService } from '@nebular/theme';
import { ConfirmDiscardDialogComponent } from '../../confirm-discard-dialog/confirm-discard-dialog.component';
import { Factor } from 'src/app/data/models/factor.model';

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
  //#endregion

  ngOnInit(): void {
    const categoryTitles = [];
    if (this.audit) {
      for (const factor of this.audit.factors) {
        for (const category of factor.categories) {
          categoryTitles.push(category.title);
        }
      }
    }

    this.formFactors = factors.map(factor => {
      const formCategories = factor.categories.map(x => {
        return { title: x.title, selected: categoryTitles.includes(x.title) };
      });

      const hasSelectedCategory = formCategories.find(x => x.selected) != undefined;
      return { categories: formCategories, title: factor.title, selected: hasSelectedCategory };
    });

    this.auditForm = this.formBuilder.group({
      auditName: [this.audit?.name, Validators.required],
      start: [this.audit?.start],
      end: [this.audit?.end],
      companyName: [this.audit?.customerData.name, Validators.required],
      sector: [this.audit?.customerData.sector, Validators.required],
      department: [this.audit?.customerData.department, Validators.required],
      title: [this.audit?.contactPerson.title, Validators.required],
      firstName: [this.audit?.contactPerson.firstName, Validators.required],
      lastName: [this.audit?.contactPerson.lastName, Validators.required],
      contactInformation: [this.audit?.contactPerson.information, Validators.required],
    });
  }

  onSubmit() {
    const filteredFactors = this.formFactors.filter(
      x => x.categories.findIndex(x => x['selected']) != -1,
    );

    filteredFactors.forEach(x => (x.categories = x.categories.filter(x => x['selected'])));

    const audit: Audit = {
      name: this.auditName.value,
      contactPerson: {
        firstName: this.firstName.value,
        lastName: this.lastName.value,
        title: this.title.value,
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
