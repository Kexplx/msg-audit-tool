import { Component, ViewChild, TemplateRef, AfterViewInit, OnInit } from '@angular/core';
import { NbDialogRef, NbDialogService } from '@nebular/theme';
import { Store } from '@ngxs/store';
import { defaultDialogOptions } from '../default-dialog-options';
import { Audit } from 'src/app/core/data/models/audit.model';
import { AddAudit } from 'src/app/core/ngxs/actions/audit.actions';
import { Location } from '@angular/common';
import { ContactPersonNewService } from 'src/app/core/http_new/contact-person-new.service';
import { Observable } from 'rxjs';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';
import { FacCritNewService } from 'src/app/core/http_new/faccrit-new.service';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';
import { filter } from 'rxjs/operators';
import { AudtiNewService } from 'src/app/core/http_new/audit-new.service';

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
    private contactPersonService: ContactPersonNewService,
    private auditService: AudtiNewService,
    private facCritService: FacCritNewService,
    private location: Location,
  ) {}

  ngOnInit() {
    this.contactPersons$ = this.contactPersonService.contactPersons$;
    this.facCrits$ = this.facCritService.facCrits$;

    this.facCritService.getFacCrits();
    this.contactPersonService.getContactPersons();
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
    this.auditService.postAudit(audit);
    this.dialogRef.close();
  }

  onCancel() {
    this.dialogRef.close();
  }
}
