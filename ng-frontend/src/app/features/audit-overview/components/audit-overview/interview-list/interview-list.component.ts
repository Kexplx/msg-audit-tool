import { Component, OnInit } from '@angular/core';
import { Audit } from 'src/app/core/data/models/audit.model';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Store } from '@ngxs/store';
import { AuditRegistryState } from 'src/app/core/ngxs/audit-registry.state';

@Component({
  selector: 'app-interview-list',
  templateUrl: './interview-list.component.html',
  styleUrls: ['./interview-list.component.scss'],
})
export class InterviewListComponent implements OnInit {
  audit$: Observable<Audit>;
  constructor(private router: Router, private store: Store) {}

}
