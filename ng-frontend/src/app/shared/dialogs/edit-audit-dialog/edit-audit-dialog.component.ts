import { Component, OnInit, TemplateRef, ViewChild, AfterViewInit } from '@angular/core';
import { NbDialogRef, NbDialogService } from '@nebular/theme';
import { Store } from '@ngxs/store';
import { AuditRegistryState } from 'src/app/ngxs/audit-registry.state';
import { Observable } from 'rxjs';
import { Audit } from 'src/app/data/models/audit.model';
import { UpdateAudit } from 'src/app/ngxs/audit.actions';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { tap } from 'rxjs/operators';
import { defaultDialogOptions } from '../default-dialog-options';

@Component({
  selector: 'app-edit-audit-dialog',
  templateUrl: './edit-audit-dialog.component.html',
  styleUrls: ['./edit-audit-dialog.component.scss'],
})
export class EditAuditDialogComponent implements OnInit, AfterViewInit {
  @ViewChild('dialog') dialog: TemplateRef<any>;
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
