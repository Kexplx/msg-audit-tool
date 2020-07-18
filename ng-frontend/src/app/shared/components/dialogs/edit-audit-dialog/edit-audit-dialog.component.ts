import { Component, OnInit, TemplateRef, ViewChild, AfterViewInit } from '@angular/core';
import { NbDialogRef, NbDialogService } from '@nebular/theme';
import { Store, Select } from '@ngxs/store';
import { Observable } from 'rxjs';
import { Location } from '@angular/common';
import { defaultDialogOptions } from '../default-dialog-options';
import { Audit } from 'src/app/core/data/models/audit.model';
import { AuditState } from 'src/app/core/ngxs/audit.state';
import { UpdateAudit } from 'src/app/core/ngxs/actions/audit.actions';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';
import { ContactPersonState } from 'src/app/core/ngxs/contact-person.state';
import { AppRouterState } from 'src/app/core/ngxs/app-router.state';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';
import { AudtiNewService } from 'src/app/core/http_new/audit-new.service';
import { ContactPersonNewService } from 'src/app/core/http_new/contact-person-new.service';
import { FacCritNewService } from 'src/app/core/http_new/faccrit-new.service';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-edit-audit-dialog',
  templateUrl: './edit-audit-dialog.component.html',
  styleUrls: ['./edit-audit-dialog.component.scss'],
})
export class EditAuditDialogComponent implements OnInit, AfterViewInit {
  @ViewChild('dialog') dialog: TemplateRef<any>;
  @Select(AppRouterState.auditId) auditId$: Observable<number>;

  contactPersons$: Observable<ContactPerson[]>;
  facCrits$: Observable<FacCrit[]>;
  audit$: Observable<Audit>;

  dialogRef: NbDialogRef<any>;
  auditId: number;

  constructor(
    private dialogService: NbDialogService,
    private auditService: AudtiNewService,
    private contactPersonService: ContactPersonNewService,
    private facCritService: FacCritNewService,
    private store: Store,
    private location: Location,
  ) {}

  ngOnInit(): void {
    this.auditId$.subscribe(id => {
      this.auditId = id;

      this.contactPersons$ = this.contactPersonService.contactPersons$;
      this.facCrits$ = this.facCritService.facCrits$;
      this.audit$ = this.auditService.audits$.pipe(map(audits => audits.find(a => a.id === id)));

      this.contactPersonService.getContactPersons();
      this.facCritService.getFacCrits();
    });
  }

  ngAfterViewInit() {
    this.dialogRef = this.dialogService.open(this.dialog, defaultDialogOptions);

    this.dialogRef.onClose.subscribe(() => {
      this.location.back();
    });

    this.audit$.subscribe(audit => audit ?? this.dialogRef.close());
  }

  onSubmit(audit: Audit) {
    this.auditService.putAudit(audit);
    this.dialogRef.close();
  }

  onCancel() {
    this.dialogRef.close();
  }
}
