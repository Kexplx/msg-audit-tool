import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { Audit } from '../data/models/audit.model';
import { IsoCategory } from '../data/models/iso-category.model';
import { isoCategories } from '../data/iso-categories';
import { Store } from '@ngxs/store';
import { AddAudit } from '../ngxs/audit.actions';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-audit',
  templateUrl: './add-audit.component.html',
  styleUrls: ['./add-audit.component.scss'],
})
export class AddAuditComponent implements OnInit {
  auditForm: FormGroup;
  audit: Audit;
  isoCategories: { checked: boolean; category: IsoCategory }[];

  constructor(private store: Store, private router: Router) {}

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

  parseDate(s: string) {
    if (s) {
      return new Date(s).getTime();
    }
  }

  ngOnInit() {
    this.auditForm = new FormGroup({
      auditName: new FormControl(null, Validators.required),
      start: new FormControl(null),
      end: new FormControl(null),
      companyName: new FormControl(null, Validators.required),
      sector: new FormControl(null, Validators.required),
      department: new FormControl(null, Validators.required),
      title: new FormControl(null, Validators.required),
      firstName: new FormControl(null, Validators.required),
      lastName: new FormControl(null, Validators.required),
      contactInformation: new FormControl(null, Validators.required),
    });
    this.isoCategories = isoCategories.map(x => ({ category: x, checked: false }));
  }

  onSubmit(categories: IsoCategory[]) {
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
      categories,
    };

    console.log(audit);

    this.store.dispatch(new AddAudit(audit)).subscribe(() => this.router.navigate(['/audits']));
  }
}
