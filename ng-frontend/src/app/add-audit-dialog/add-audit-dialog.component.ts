import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { NbDialogRef, NbDialogService } from '@nebular/theme';
import { Router, CanDeactivate } from '@angular/router';
import { IsoCategory } from '../data/models/iso-category.model';
import { isoCategories } from '../data/iso-categories';
import { Audit } from '../data/models/audit.model';
import { AddAudit } from '../ngxs/audit.actions';
import { Store } from '@ngxs/store';
import { ConfirmDiscardDialogComponent } from '../shared/confirm-discard-dialog/confirm-discard-dialog.component';

@Component({
  selector: 'app-add-audit-dialog',
  templateUrl: './add-audit-dialog.component.html',
  styleUrls: ['./add-audit-dialog.component.scss'],
})
export class AddAuditDialogComponent implements OnInit {
  auditForm: FormGroup;
  categories: IsoCategory[];
  selectedCategories: IsoCategory[];

  constructor(
    private store: Store,
    private dialogRef: NbDialogRef<any>,
    private router: Router,
    private dialogService: NbDialogService,
  ) {}

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
    this.categories = isoCategories;
    this.selectedCategories = [];

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

    this.dialogRef.onClose.subscribe(() => {
      this.router.navigate(['/audits']);
    });
  }

  addParentCategory(parentCategory: IsoCategory) {
    this.selectedCategories.push({ ...parentCategory });
  }

  removeParentCategory(parentCategory: IsoCategory) {
    const index = this.selectedCategories.indexOf(parentCategory);
    this.selectedCategories.splice(index, 1);
  }

  addChildCategory(childCategory: IsoCategory, parentCategory: IsoCategory) {
    const parent = this.selectedCategories.find(x => x.title == parentCategory.title);

    if (!parent) {
      this.selectedCategories.push({
        title: parentCategory.title,
        children: [childCategory],
      });
    } else {
      parent.children.push(childCategory);
    }
  }

  removeChildCategory(childCategory: IsoCategory, parentCategory: IsoCategory) {
    parentCategory = this.selectedCategories.find(x => x.title == parentCategory.title);
    parentCategory.children = parentCategory.children.filter(x => x != childCategory);
  }

  isChecked(title: string) {
    return (
      this.selectedCategories.find(
        x => x.title == title || x.children?.map(x => x.title).includes(title),
      ) != undefined
    );
  }

  parseDate(s: string) {
    if (s) {
      return new Date(s).getTime();
    }
  }

  sortCategoriesByTitle(categories: IsoCategory[]) {
    categories = categories.sort((a: IsoCategory, b: IsoCategory) => (a.title > b.title ? 1 : -1));

    for (const category of categories) {
      if (category.children) {
        category.children = category.children.sort((a: IsoCategory, b: IsoCategory) =>
          a.title > b.title ? 1 : -1,
        );
      }
    }

    return categories;
  }

  onSubmit() {
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
      categories: this.sortCategoriesByTitle(this.selectedCategories),
    };

    this.store.dispatch(new AddAudit(audit)).subscribe(() => this.dialogRef.close());
  }

  onCancel() {
    if (this.auditForm.touched && this.auditForm.dirty) {
      const k = this.dialogService.open(ConfirmDiscardDialogComponent, {
        autoFocus: false,
        closeOnBackdropClick: false,
      });

      k.componentRef.instance.onDiscardConfirm.subscribe((cancelConfirmed: boolean) => {
        if (cancelConfirmed) {
          this.dialogRef.close();
        }
      });
    } else {
      this.dialogRef.close();
    }
  }
}
