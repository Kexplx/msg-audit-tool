import { Component, OnInit, ViewChild, TemplateRef } from '@angular/core';
import { Observable } from 'rxjs';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';
import { NbDialogService, NbDialogRef } from '@nebular/theme';
import { defaultDialogOptions } from '../../../shared/components/dialogs/default-dialog-options';
import { Location } from '@angular/common';
import { map, filter } from 'rxjs/operators';
import { ContactPersonStore } from 'src/app/core/stores/contact-person.store';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-edit-contact-person-dialog',
  templateUrl: './edit-contact-person-dialog.component.html',
  styleUrls: ['./edit-contact-person-dialog.component.scss'],
})
export class EditContactPersonDialogComponent implements OnInit {
  @ViewChild('dialog') dialog: TemplateRef<any>;
  contactPerson$: Observable<ContactPerson>;

  dialogRef: NbDialogRef<any>;

  constructor(
    private location: Location,
    private contactPersonStore: ContactPersonStore,
    private dialogService: NbDialogService,
    private activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit() {
    const contactPersonId: number = +this.activatedRoute.snapshot.params.id;
    this.contactPerson$ = this.contactPersonStore.contactPersons$.pipe(
      filter(contactPersons => contactPersons != null),
      map(contactPersons => contactPersons.find(cp => cp.id === contactPersonId)),
    );
  }

  ngAfterViewInit() {
    this.dialogRef = this.dialogService.open(this.dialog, defaultDialogOptions);
    this.dialogRef.onClose.subscribe(() => {
      this.location.back();
    });
  }

  onSubmit(contactPerson: ContactPerson) {
    this.contactPersonStore.updateContactPerson(contactPerson);
    this.dialogRef.close();
  }

  onCancel() {
    this.dialogRef.close();
  }
}
