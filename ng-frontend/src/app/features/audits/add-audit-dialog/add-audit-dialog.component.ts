import { Component, ViewChild, TemplateRef, AfterViewInit, OnInit } from '@angular/core';
import { NbDialogRef, NbDialogService } from '@nebular/theme';
import { defaultDialogOptions } from '../../../shared/components/dialogs/default-dialog-options';
import { Audit } from 'src/app/core/data/models/audit.model';
import { Location } from '@angular/common';
import { Observable } from 'rxjs';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';
import { AuditStore } from 'src/app/core/data/stores/audit.store';
import { ContactPersonStore } from 'src/app/core/data/stores/contact-person.store';
import { FacCritStore } from 'src/app/core/data/stores/faccrit.store';

@Component({
  selector: 'app-add-audit-dialog',
  templateUrl: './add-audit-dialog.component.html',
  styleUrls: ['./add-audit-dialog.component.scss'],
})
export class AddAuditDialogComponent implements AfterViewInit, OnInit {
  @ViewChild('dialog') dialog: TemplateRef<any>;
  dialogRef: NbDialogRef<any>;

  contactPersons$: Observable<ContactPerson[]>;
  facCrits$: Observable<FacCrit[]>;

  constructor(
    private dialogService: NbDialogService,
    private contactPersonStore: ContactPersonStore,
    private auditStore: AuditStore,
    private facCritStore: FacCritStore,
    private location: Location,
  ) {}

  ngOnInit() {
    this.contactPersons$ = this.contactPersonStore.contactPersons$;
    this.facCrits$ = this.facCritStore.facCrits$;
  }

  ngAfterViewInit() {
    this.dialogRef = this.dialogService.open(this.dialog, {
      ...defaultDialogOptions,
      autoFocus: true,
    });
    this.dialogRef.onClose.subscribe(() => {
      this.location.back();
    });
  }

  onSubmit(audit: Audit) {
    this.auditStore.addAudit(audit);
    this.dialogRef.close();
  }

  onCancel() {
    this.dialogRef.close();
  }
}
