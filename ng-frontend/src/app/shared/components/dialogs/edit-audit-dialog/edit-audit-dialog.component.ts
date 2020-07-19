import { Component, OnInit, TemplateRef, ViewChild, AfterViewInit } from '@angular/core';
import { NbDialogRef, NbDialogService } from '@nebular/theme';
import { Select } from '@ngxs/store';
import { Observable } from 'rxjs';
import { Location } from '@angular/common';
import { defaultDialogOptions } from '../default-dialog-options';
import { Audit } from 'src/app/core/data/models/audit.model';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';
import { AppRouterState } from 'src/app/core/ngxs/app-router.state';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';
import { map } from 'rxjs/operators';
import { AuditStore } from 'src/app/core/stores/audit.store';
import { FacCritStore } from 'src/app/core/stores/faccrit.store';
import { ContactPersonStore } from 'src/app/core/stores/contact-person.store';

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
    private auditStore: AuditStore,
    private contactPersonStore: ContactPersonStore,
    private facCritStore: FacCritStore,
    private location: Location,
  ) {}

  ngOnInit(): void {
    this.auditId$.subscribe(id => {
      this.auditId = id;

      this.contactPersons$ = this.contactPersonStore.contactPersons$;
      this.facCrits$ = this.facCritStore.facCrits$;
      this.audit$ = this.auditStore.audits$.pipe(map(audits => audits.find(a => a.id === id)));

      this.contactPersonStore.loadContactPersons();
      this.facCritStore.loadFacCrits();
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
    this.auditStore.updateAudit(audit);
    this.dialogRef.close();
  }

  onCancel() {
    this.dialogRef.close();
  }
}
