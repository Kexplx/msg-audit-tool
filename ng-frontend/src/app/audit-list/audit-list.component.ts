import { Component, OnInit } from '@angular/core';
import { Select } from '@ngxs/store';
import { AuditRegistryState } from '../ngxs/audit-registry.state';
import { Observable } from 'rxjs';
import { Audit } from '../data/models/audit.model';
import { NbDialogService } from '@nebular/theme';
import { AddAuditDialogComponent } from '../add-audit-dialog/add-audit-dialog.component';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-audit-list',
  templateUrl: './audit-list.component.html',
  styleUrls: ['./audit-list.component.scss'],
})
export class AuditListComponent implements OnInit {
  @Select(AuditRegistryState.audits) audits$: Observable<Audit[]>;

  constructor(private dialogService: NbDialogService, private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.url.subscribe(urlSegment => {
      if (urlSegment[1]?.path === 'neu') {
        this.dialogService.open(AddAuditDialogComponent);
      }
    });
  }

  onAddAuditClick() {
    this.dialogService.open(AddAuditDialogComponent);
  }
}
