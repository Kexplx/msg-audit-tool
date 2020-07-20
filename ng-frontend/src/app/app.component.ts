import { Component, OnInit } from '@angular/core';
import { NbSidebarService } from '@nebular/theme';
import { Observable } from 'rxjs';
import { Audit } from './core/data/models/audit.model';
import { AuditStore } from './core/stores/audit.store';
import { IdService } from './core/id.service';
import { ContactPersonStore } from './core/stores/contact-person.store';
import { FacCritStore } from './core/stores/faccrit.store';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  audits$: Observable<Audit[]>;

  constructor(
    private sidebarService: NbSidebarService,
    private auditStore: AuditStore,
    private idService: IdService,
    private contactPersonStore: ContactPersonStore,
    private facCritStore: FacCritStore,
  ) {}

  ngOnInit() {
    this.audits$ = this.auditStore.audits$;

    // Load initial data and listen to router navigations.
    this.auditStore.loadAudits();
    this.contactPersonStore.loadContactPersons();
    this.facCritStore.loadFacCrits();

    this.idService.listenToNaviagtion();
  }

  toggleSidebar() {
    this.sidebarService.toggle();
  }
}
