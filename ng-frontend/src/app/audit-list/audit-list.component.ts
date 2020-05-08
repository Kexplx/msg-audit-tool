import { Component, OnInit } from '@angular/core';
import { Select } from '@ngxs/store';
import { AuditRegistryState } from '../ngxs/audit-registry.state';
import { Observable } from 'rxjs';
import { Audit } from '../data/models/audit.model';
import { NbDialogService, NbDialogConfig } from '@nebular/theme';
import { AddAuditDialogComponent } from '../add-audit-dialog/add-audit-dialog.component';
import { ActivatedRoute, Router, UrlSegment } from '@angular/router';
import { EditAuditDialogComponent } from '../shared/dialogs/edit-audit-dialog/edit-audit-dialog.component';

const defaultDialogOptions: Partial<NbDialogConfig> = {
  autoFocus: false,
  closeOnBackdropClick: false,
  closeOnEsc: false,
};

@Component({
  selector: 'app-audit-list',
  templateUrl: './audit-list.component.html',
  styleUrls: ['./audit-list.component.scss'],
})
export class AuditListComponent implements OnInit {
  @Select(AuditRegistryState.audits) audits$: Observable<Audit[]>;

  constructor(private dialogService: NbDialogService, private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.url.subscribe((urlsegments: UrlSegment[]) => {
      const url = urlsegments
        .map(x => x.path)
        .toString()
        .replace(/,/g, '/');
      switch (true) {
        case /audits\/neu/.test(url):
          this.dialogService.open(AddAuditDialogComponent, {
            ...defaultDialogOptions,
          });
          break;
        case /audits\/.*\/edit/.test(url):
          this.dialogService.open(EditAuditDialogComponent, {
            ...defaultDialogOptions,
            context: { id: urlsegments[1].path },
          });
        default:
          break;
      }
    });
  }
}
