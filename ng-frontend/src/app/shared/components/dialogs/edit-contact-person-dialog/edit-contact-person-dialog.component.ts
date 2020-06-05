import { Component, OnInit, ViewChild, TemplateRef } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';
import { Store } from '@ngxs/store';
import { AuditRegistryState } from 'src/app/core/ngxs/audit-registry.state';
import { NbDialogService, NbDialogRef } from '@nebular/theme';
import { defaultDialogOptions } from '../default-dialog-options';
import { Location } from '@angular/common';
import { tap } from 'rxjs/operators';
import { UpdateContactPerson } from 'src/app/core/ngxs/audit.actions';

@Component({
  selector: 'app-edit-contact-person-dialog',
  templateUrl: './edit-contact-person-dialog.component.html',
  styleUrls: ['./edit-contact-person-dialog.component.scss'],
})
export class EditContactPersonDialogComponent implements OnInit {
  @ViewChild('dialog') dialog: TemplateRef<any>;
  dialogRef: NbDialogRef<any>;
  private id: string;

  contactPerson$: Observable<ContactPerson>;

  constructor(
    private router: Router,
    private location: Location,
    private store: Store,
    private dialogService: NbDialogService,
  ) {}
  ngOnInit() {
    this.id = this.router.url.split('/')[2];
    this.contactPerson$ = this.store.select(AuditRegistryState.contactPerson(this.id));
  }

  ngAfterViewInit() {
    this.dialogRef = this.dialogService.open(this.dialog, defaultDialogOptions);

    this.dialogRef.onClose.subscribe(() => {
      this.location.back();
    });

    this.contactPerson$
      .pipe(
        tap(contactPerson => {
          if (!contactPerson) {
            throw Error(`Audit with id: ${this.id} not found`);
          }
        }),
      )
      .subscribe(null, () => {
        this.dialogRef.close();
      });
  }

  onSubmit(contactPereson: ContactPerson) {
    this.store
      .dispatch(new UpdateContactPerson(this.id, contactPereson))
      .subscribe(() => this.dialogRef.close());
  }

  onCancel() {
    this.dialogRef.close();
  }
}
