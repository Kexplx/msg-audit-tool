import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ConfirmDiscardDialogComponent } from '../../dialogs/confirm-discard-dialog/confirm-discard-dialog.component';
import { NbDialogService } from '@nebular/theme';

@Component({
  selector: 'app-contact-person-form',
  templateUrl: './contact-person-form.component.html',
  styleUrls: ['./contact-person-form.component.scss'],
})
export class ContactPersonFormComponent implements OnInit {
  @Input() contactPerson: ContactPerson;
  @Input() submitButtonName: string;

  @Output() formSubmitted = new EventEmitter<Partial<ContactPerson>>();
  @Output() cancelled = new EventEmitter<any>();

  contactPersonForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private dialogService: NbDialogService) {}

  get firstName() {
    return this.contactPersonForm.get('firstName');
  }

  get lastName() {
    return this.contactPersonForm.get('lastName');
  }

  get salutation() {
    return this.contactPersonForm.get('salutation');
  }

  get title() {
    return this.contactPersonForm.get('title');
  }

  get companyName() {
    return this.contactPersonForm.get('companyName');
  }

  get department() {
    return this.contactPersonForm.get('department');
  }

  get sector() {
    return this.contactPersonForm.get('sector');
  }

  get corporateDivision() {
    return this.contactPersonForm.get('corporateDivision');
  }

  get contactInformation() {
    return this.contactPersonForm.get('contactInformation');
  }

  ngOnInit() {
    this.contactPersonForm = this.formBuilder.group({
      firstName: [null, Validators.required],
      lastName: [null, Validators.required],
      salutation: [null, Validators.required],
      title: [null, Validators.required],
      companyName: [null, Validators.required],
      department: [null, Validators.required],
      sector: [null, Validators.required],
      corporateDivision: [null, Validators.required],
      contactInformation: [null],
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

  onCancel() {
    if (this.contactPersonForm.dirty && this.contactPersonForm.touched) {
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
