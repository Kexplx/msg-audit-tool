import { Component, OnInit } from '@angular/core';
import { NbDialogService } from '@nebular/theme';
import { AddAuditDialogComponent } from './add-audit-dialog/add-audit-dialog.component';

@Component({
  selector: 'app-audit-list',
  templateUrl: './audit-list.component.html',
  styleUrls: ['./audit-list.component.scss'],
})
export class AuditListComponent implements OnInit {
  constructor(private dialogService: NbDialogService) {}

  ngOnInit(): void {}

  onAddAuditClick() {
    this.dialogService.open(AddAuditDialogComponent);
  }
}
