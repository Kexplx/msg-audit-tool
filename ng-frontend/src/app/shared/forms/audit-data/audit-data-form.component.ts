import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Audit } from 'src/app/data/models/audit.model';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { categories } from 'src/app/data/categories';
import { Category } from 'src/app/data/models/category.model';
import { NbDialogService } from '@nebular/theme';
import { ConfirmDiscardDialogComponent } from '../../confirm-discard-dialog/confirm-discard-dialog.component';
import { CustomerData } from 'src/app/data/models/customer-data.model';
import { ContactPerson } from 'src/app/data/models/contact-person.model';

class CategoryFormObject implements Category {
  title: string;
  children?: CategoryFormObject[];
  selected: boolean;
}

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

  categories: Category[];
  selectedCategories: Category[];
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
    this.categories = categories;
    this.selectedCategories = this.audit ? [...this.audit.categories] : [];
    this.selectedCategories.forEach(x => {
      x.children = x.children ? [...x.children] : null;
    });

    // this.formBuilder.group(new AuditFormObject());

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
  addParentCategory(parentCategory: Category) {
    this.selectedCategories.push({ ...parentCategory });
  }

  removeParentCategory(parentCategory: Category) {
    const index = this.selectedCategories.indexOf(parentCategory);
    this.selectedCategories.splice(index, 1);
  }

  addChildCategory(childCategory: Category, parentCategory: Category) {
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

  removeChildCategory(childCategory: Category, parentCategory: Category) {
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
    return s ? new Date(s).getTime() : undefined;
  }

  sortCategoriesByTitle(categories: Category[]) {
    categories = categories.sort((a: Category, b: Category) => (a.title > b.title ? 1 : -1));

    for (const category of categories) {
      if (category.children) {
        category.children = category.children.sort((a: Category, b: Category) =>
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

    this.audit = audit;
    this.formSubmitted.emit(this.audit);
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
}
