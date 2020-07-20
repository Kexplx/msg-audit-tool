import { Component, OnInit, TemplateRef, ViewChild, AfterViewInit } from '@angular/core';
import { NbDialogRef, NbDialogService } from '@nebular/theme';
import { Observable } from 'rxjs';
import { Location } from '@angular/common';
import { defaultDialogOptions } from '../../../shared/components/dialogs/default-dialog-options';
import { Audit } from 'src/app/core/data/models/audit.model';
import { ContactPerson } from 'src/app/core/data/models/contact-person.model';
import { FacCrit } from 'src/app/core/data/models/faccrit.model';
import { map } from 'rxjs/operators';
import { AuditStore } from 'src/app/core/data/stores/audit.store';
import { FacCritStore } from 'src/app/core/data/stores/faccrit.store';
import { ContactPersonStore } from 'src/app/core/data/stores/contact-person.store';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-edit-audit-dialog',
  templateUrl: './edit-audit-dialog.component.html',
  styleUrls: ['./edit-audit-dialog.component.scss'],
})
export class EditAuditDialogComponent implements OnInit, AfterViewInit {
  @ViewChild('dialog') dialog: TemplateRef<any>;

  contactPersons$: Observable<ContactPerson[]>;
  facCrits$: Observable<FacCrit[]>;
  audit$: Observable<Audit>;

  dialogRef: NbDialogRef<any>;

  constructor(
    private dialogService: NbDialogService,
    private auditStore: AuditStore,
    private contactPersonStore: ContactPersonStore,
    private facCritStore: FacCritStore,
    private activatedRoute: ActivatedRoute,
    private location: Location,
  ) {}

  ngOnInit(): void {
    const auditId: number = +this.activatedRoute.snapshot.params.id;

    this.contactPersons$ = this.contactPersonStore.contactPersons$;
    this.facCrits$ = this.facCritStore.facCrits$;
    this.audit$ = this.auditStore.audits$.pipe(map(audits => audits.find(a => a.id === auditId)));
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
