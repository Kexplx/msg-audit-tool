import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';
import { FormBuilder, Validators } from '@angular/forms';
import { NbDialogService } from '@nebular/theme';
import { AbstractFormComponent } from '../abstract-form-component';

@Component({
  selector: 'app-contact-person-form',
  templateUrl: './contact-person-form.component.html',
  styleUrls: ['./contact-person-form.component.scss'],
})
export class ContactPersonFormComponent extends AbstractFormComponent implements OnInit {
  @Input() contactPerson: ContactPerson;
  @Input() submitButtonName: string;

  @Output() formSubmitted = new EventEmitter<Partial<ContactPerson>>();

  constructor(private formBuilder: FormBuilder, protected dialogService: NbDialogService) {
    super(dialogService);
  }

  get firstName() {
    return this.formGroup.get('firstName');
  }

  get lastName() {
    return this.formGroup.get('lastName');
  }

  get salutation() {
    return this.formGroup.get('salutation');
  }

  get title() {
    return this.formGroup.get('title');
  }

  get companyName() {
    return this.formGroup.get('companyName');
  }

  get department() {
    return this.formGroup.get('department');
  }

  get sector() {
    return this.formGroup.get('sector');
  }

  get corporateDivision() {
    return this.formGroup.get('corporateDivision');
  }

  get contactInformation() {
    return this.formGroup.get('contactInformation');
  }

  ngOnInit() {
    this.formGroup = this.formBuilder.group({
      firstName: [this.contactPerson?.firstName, Validators.required],
      lastName: [this.contactPerson?.lastName, Validators.required],
      salutation: [this.contactPerson?.salutation, Validators.required],
      title: [this.contactPerson?.title, Validators.required],
      companyName: [this.contactPerson?.companyName, Validators.required],
      department: [this.contactPerson?.department, Validators.required],
      sector: [this.contactPerson?.sector, Validators.required],
      corporateDivision: [this.contactPerson?.corporateDivision, Validators.required],
      contactInformation: [this.contactPerson?.contactInformation],
    });
  }

  onSubmit() {
    const contactPerson: Partial<ContactPerson> = {
      firstName: this.firstName.value,
      lastName: this.lastName.value,
      salutation: this.salutation.value,
      title: this.title.value,
      companyName: this.companyName.value,
      department: this.department.value,
      sector: this.sector.value,
      corporateDivision: this.corporateDivision.value,
      contactInformation: this.contactInformation.value,
    };

    this.formSubmitted.emit(contactPerson);
  }
}
