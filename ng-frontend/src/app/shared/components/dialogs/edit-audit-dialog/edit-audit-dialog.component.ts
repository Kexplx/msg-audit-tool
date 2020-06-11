import { Component, OnInit, TemplateRef, ViewChild, AfterViewInit } from '@angular/core';
import { NbDialogRef, NbDialogService } from '@nebular/theme';
import { Store, Select } from '@ngxs/store';
import { Observable } from 'rxjs';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { tap } from 'rxjs/operators';
import { defaultDialogOptions } from '../default-dialog-options';
import { Audit } from 'src/app/core/data/models/audit.model';
import { AuditRegistryState } from 'src/app/core/ngxs/audit-registry.state';
import { UpdateAudit } from 'src/app/core/ngxs/actions/audit.actions';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';
import { ContactPersonState } from 'src/app/core/ngxs/contact-people.state';

@Component({
  selector: 'app-edit-audit-dialog',
  templateUrl: './edit-audit-dialog.component.html',
  styleUrls: ['./edit-audit-dialog.component.scss'],
})
export class EditAuditDialogComponent implements OnInit, AfterViewInit {
  @ViewChild('dialog') dialog: TemplateRef<any>;
  @Select(ContactPersonState.contactPeople) contactPeople$: Observable<ContactPerson[]>;
  dialogRef: NbDialogRef<any>;

  audit$: Observable<Audit>;
  id: string;

  constructor(
    private dialogService: NbDialogService,
    private store: Store,
    private router: Router,
    private location: Location,
  ) {}

  ngOnInit(): void {
    this.id = this.router.url.split('/')[2];
    this.audit$ = this.store.select(AuditRegistryState.audit(this.id));
  }

  ngAfterViewInit() {
    this.dialogRef = this.dialogService.open(this.dialog, defaultDialogOptions);

    this.dialogRef.onClose.subscribe(() => {
      this.location.back();
    });

    this.audit$
      .pipe(
        tap(audit => {
          if (!audit) {
            throw Error(`Audit with id: ${this.id} not found`);
          }
        }),
      )
      .subscribe(null, () => {
        this.dialogRef.close();
      });
  }

  onSubmit(audit: Audit) {
    this.store.dispatch(new UpdateAudit(this.id, audit)).subscribe(() => this.dialogRef.close());
  }

  onCancel() {
    this.dialogRef.close();
  }
}
