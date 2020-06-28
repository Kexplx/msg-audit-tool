import { Component, OnInit, ViewChild, TemplateRef } from '@angular/core';
import { Observable } from 'rxjs';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';
import { Store, Select } from '@ngxs/store';
import { NbDialogService, NbDialogRef } from '@nebular/theme';
import { defaultDialogOptions } from '../default-dialog-options';
import { Location } from '@angular/common';
import { ContactPersonState } from 'src/app/core/ngxs/contact-people.state';
import { UpdateContactPerson } from 'src/app/core/ngxs/actions/contact-person.action';
import { AppRouterState } from 'src/app/core/ngxs/app-router.state';

@Component({
  selector: 'app-edit-contact-person-dialog',
  templateUrl: './edit-contact-person-dialog.component.html',
  styleUrls: ['./edit-contact-person-dialog.component.scss'],
})
export class EditContactPersonDialogComponent implements OnInit {
  @ViewChild('dialog') dialog: TemplateRef<any>;
  @Select(AppRouterState.contactPersonId) contactPersonId$: Observable<string>;
  contactPerson$: Observable<ContactPerson>;

  dialogRef: NbDialogRef<any>;
  private contactPersonId: string;

  constructor(
    private location: Location,
    private store: Store,
    private dialogService: NbDialogService,
  ) {}
  ngOnInit() {
    this.contactPersonId$.subscribe(id => {
      this.contactPersonId = id;
      this.contactPerson$ = this.store.select(ContactPersonState.contactPerson(id));
    });
  }

  ngAfterViewInit() {
    this.dialogRef = this.dialogService.open(this.dialog, defaultDialogOptions);

    this.dialogRef.onClose.subscribe(() => {
      this.location.back();
    });
  }

  onSubmit(contactPereson: ContactPerson) {
    this.store
      .dispatch(new UpdateContactPerson(this.contactPersonId, contactPereson))
      .subscribe(() => this.dialogRef.close());
  }

  onCancel() {
    this.dialogRef.close();
  }
}
