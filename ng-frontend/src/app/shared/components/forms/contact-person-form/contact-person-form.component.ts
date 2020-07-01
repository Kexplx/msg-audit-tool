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

  get forename() {
    return this.formGroup.get('forename');
  }

  get surname() {
    return this.formGroup.get('surname');
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
      forename: [this.contactPerson?.forename, Validators.required],
      surname: [this.contactPerson?.surname, Validators.required],
      salutation: [this.contactPerson?.salutation],
      title: [this.contactPerson?.title],
      companyName: [this.contactPerson?.companyName, Validators.required],
      department: [this.contactPerson?.department],
      sector: [this.contactPerson?.sector],
      corporateDivision: [this.contactPerson?.corporateDivision, Validators.required],
      contactInformation: [this.contactPerson?.contactInformation],
    });
  }

  onSubmit() {
    const contactPerson: Partial<ContactPerson> = {
      forename: this.forename.value,
      surname: this.surname.value,
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
